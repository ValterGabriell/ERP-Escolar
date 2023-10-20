package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.controller.StudentsController;
import io.github.ValterGabriell.FrequenciaAlunos.domain.admins.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.domain.frequency.Frequency;
import io.github.ValterGabriell.FrequenciaAlunos.domain.students.Student;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.AdminRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.FrequencyRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.StudentsRepository;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.students.GetStudent;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.students.InsertStudents;
import io.github.ValterGabriell.FrequenciaAlunos.validation.FieldValidationImpl;
import io.github.ValterGabriell.FrequenciaAlunos.validation.StudentValidationImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static io.github.ValterGabriell.FrequenciaAlunos.validation.ExceptionsValues.STUDENT_ALREADY_SAVED;
import static io.github.ValterGabriell.FrequenciaAlunos.validation.ExceptionsValues.STUDENT_ALREADY_SAVED_TO_ADMINISTRATOR;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class StudentsService {
    private final StudentsRepository studentsRepository;
    private final FrequencyRepository frequencyRepository;
    private final AdminRepository adminRepository;

    public StudentsService(
            StudentsRepository studentsRepository,
            FrequencyRepository frequencyRepository,
            AdminRepository adminRepository) {
        this.studentsRepository = studentsRepository;
        this.frequencyRepository = frequencyRepository;
        this.adminRepository = adminRepository;
    }

    /**
     * Método para inserir um estudante no banco de dados.
     *
     * @param request   requisição com os dados para a inserção, incluindo CPF (formato: XXXXXXXXXXX),
     *                  CPF deve conter exatamente 11 caracteres numéricos, além do nome de usuário do estudante.
     *                  O nome de usuário deve conter apenas letras.
     * @param adminSkId O identificador do administrador responsável pela inserção.
     * @param tenant    O inquilino associado ao estudante.
     * @return O objeto do estudante criado.
     */
    public GetStudent insertStudentIntoDatabase(InsertStudents request,
                                                String adminSkId,
                                                Integer tenant) {
        boolean present = studentsRepository.findById(request.getCpf()).isPresent();

        if (present) {
            throw new RequestExceptions(STUDENT_ALREADY_SAVED);
        }



        if (!request.usernameIsNotNull()
                && !request.isFieldHasNumberExcatlyOfChars(request.getCpf(), 11)
                && !request.emailIsNotNull()
        ) throw new RequestExceptions("Erro desconhecido ao gerar estudante, contate o desenvolvedor!");

        Admin admin =
                checkIfStudentAlreadyInsertedToAdminAndReturnsAdminIfIsNot(adminSkId, request.getCpf(), tenant);
        if (admin == null) {
            throw new RequestExceptions(STUDENT_ALREADY_SAVED_TO_ADMINISTRATOR);
        }

        Student student;
        student = request.toModel(admin.getTenant());
        student.setAdmin(admin.getCnpj());

        Frequency frequency = new Frequency(student.getTenant());
        frequency.setId(student.getId());
        frequency.setDaysList(new ArrayList<>());

        //inserir frequencia no estudante
        frequencyRepository.save(frequency);

        //gerar resposta para o cliente
        Student studentSaved = studentsRepository.save(student);
        studentSaved.add(linkTo(methodOn(StudentsController.class)
                .getAllStudents(null, 0)).withSelfRel());
        return generateStudentResponse(studentSaved);
    }

    /**
     * Método privado para gerar a resposta de um estudante (GetStudent) com base nos dados do estudante salvo.
     *
     * @param studentSaved O estudante recém-salvo cujos dados serão usados para criar a resposta.
     * @return Uma instância de GetStudent com os dados do estudante salvo.
     */
    private static GetStudent generateStudentResponse(Student studentSaved) {
        GetStudent getStudent;
        getStudent = new GetStudent(
                studentSaved.getId(),
                studentSaved.getFirstName(),
                studentSaved.getEmail(),
                studentSaved.getStartDate(),
                studentSaved.getAdmin(),
                studentSaved.getLinks()
        );
        return getStudent;
    }

    /**
     * Método privado para verificar
     * se um estudante já foi associado a um administrador e retorna o administrador se não foi.
     *
     * @param adminSkId O identificador do administrador a ser verificado.
     * @param studentId O ID do estudante a ser verificado.
     * @param tenantId  O inquilino associado ao administrador.
     * @return O administrador se o estudante já estiver associado a ele; caso contrário, retorna null.
     * @throws RequestExceptions Se o administrador com o `adminSkId` especificado não for encontrado.
     */
    private Admin checkIfStudentAlreadyInsertedToAdminAndReturnsAdminIfIsNot(
            String adminSkId,
            String studentId,
            Integer tenantId) {
        Admin admin = adminRepository.findBySkid(adminSkId, tenantId)
                .orElseThrow(() -> new RequestExceptions("Administrador " + adminSkId + " não encontrado!"));
        for (Student student : admin.getStudents()) {
            if (student.getId().equals(studentId)) {
                return admin;
            }
        }
        if (admin.getStudents().isEmpty()) {
            return admin;
        }
        return null;
    }


    /**
     * Método para obter todos os estudantes do banco de dados.
     * @return Uma lista de todos os estudantes do banco de dados.
     */
    public Page<GetStudent> getAllStudentsFromDatabase(Pageable pageable, int tenantId) {
        Page<Student> allStudents = studentsRepository.findAll(pageable);

        Predicate<Student> filterByTenant = (student) -> {
            return student.getTenant() == tenantId;
        };

        Function<Student, GetStudent> mapToGetStudent = (student) -> {
            return new GetStudent(
                    student.getId(),
                    student.getFirstName(),
                    student.getEmail(),
                    student.getStartDate(),
                    student.getAdmin(),
                    student.getLinks());
        };

        List<GetStudent> studentsFilteredByTenantId =
                allStudents.stream()
                        .filter(filterByTenant)
                        .map(mapToGetStudent).collect(Collectors.toList());

        Collections.sort(studentsFilteredByTenantId);
        Page<GetStudent> page = new PageImpl<>(studentsFilteredByTenantId);
        return page;
    }

    private Student validateIfStudentExistsAndReturnIfExist(String studentId, int tenantId) {
        StudentValidationImpl studentValidation = new StudentValidationImpl();
        return studentValidation.validateIfStudentExistsAndReturnIfExist(studentsRepository, studentId, tenantId);
    }

    /**
     * Método para obter 1 estudante pelo cpf e tenant.
     * @return Um estudante.
     */
    public GetStudent getStudentByCpf(String cpf, int tenantId) {
        Student student = validateIfStudentExistsAndReturnIfExist(cpf, tenantId);
        return new GetStudent(
                student.getId(),
                student.getFirstName(),
                student.getEmail(),
                student.getStartDate(),
                student.getAdmin(),
                student.getLinks()
        );
    }

    /**
     * Método para excluir um estudante do banco de dados.
     *
     * @param studentId O ID do estudante a ser excluído.
     */
    public void deleteStudent(String studentId, int tenantId) {
        Student student = validateIfStudentExistsAndReturnIfExist(studentId, tenantId);
        studentsRepository.delete(student);
    }
}
