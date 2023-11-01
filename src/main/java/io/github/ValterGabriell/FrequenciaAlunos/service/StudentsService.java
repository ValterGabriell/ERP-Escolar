package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.controller.StudentsController;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Frequency;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Parent;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Student;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.AdminRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.FrequencyRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.ParentsRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.StudentsRepository;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.PatternResponse;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.students.GetStudent;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.students.InsertStudents;
import io.github.ValterGabriell.FrequenciaAlunos.util.GenerateSKId;
import io.github.ValterGabriell.FrequenciaAlunos.validation.StudentValidation;
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

import static io.github.ValterGabriell.FrequenciaAlunos.exceptions.ExceptionsValues.STUDENT_ALREADY_SAVED_TO_ADMINISTRATOR;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class StudentsService {
    private final StudentsRepository studentsRepository;
    private final FrequencyRepository frequencyRepository;
    private final AdminRepository adminRepository;
    private final ParentsRepository parentsRepository;

    public StudentsService(
            StudentsRepository studentsRepository,
            FrequencyRepository frequencyRepository,
            AdminRepository adminRepository, ParentsRepository parentsRepository) {
        this.studentsRepository = studentsRepository;
        this.frequencyRepository = frequencyRepository;
        this.adminRepository = adminRepository;
        this.parentsRepository = parentsRepository;
    }

    public PatternResponse<String> insertStudentIntoDatabase(InsertStudents request,
                                            String adminCnpj,
                                            Integer tenant,
                                            String parentIdentifier
    ) {
        Parent parent = parentsRepository.findByIdentifierNumberAndTenant(parentIdentifier, tenant).orElseThrow(() -> {
            throw new RequestExceptions("Genitor não encontrado! -> " + parentIdentifier);
        });

        validateIfStudentFieldsIsOk(request);

        Admin admin =
                checkIfStudentAlreadyInsertedToAdminAndReturnsAdminIfIsNot(adminCnpj, request.getStudentId(), tenant);
        Student student;
        student = request.toModel(admin.getTenant());
        student.setAdmin(admin.getCnpj());
        student.setSchoolClass(" ");
        student.setSecondName(request.getSecondName());
        student.setSkid(GenerateSKId.generateSkId());
        parent.getStudents().add(student);

        Frequency frequency = new Frequency(student.getTenant());
        frequency.setFrequencyId(student.getStudentId());
        frequency.setDaysList(new ArrayList<>());
        frequency.setSkid(GenerateSKId.generateSkId());

        parentsRepository.save(parent);
        frequencyRepository.save(frequency);
        Student studentSaved = studentsRepository.save(student);

        admin.getStudents().add(studentSaved);
        adminRepository.save(admin);

        studentSaved.add(linkTo(methodOn(StudentsController.class)
                .getStudentBySkId(studentSaved.getSkid(), 0)).withSelfRel());

        return new PatternResponse<>(studentSaved.getSkid(), studentSaved.getLinks());
    }

    private static void validateIfStudentFieldsIsOk(InsertStudents request) {
        if (!request.usernameIsNotNull()
                && !request.isFieldHasNumberExcatlyOfChars(request.getStudentId(), 11)
                && !request.emailIsNotNull()
        ) throw new RequestExceptions("Erro desconhecido ao gerar estudante, contate o desenvolvedor!");
    }

    private Admin checkIfStudentAlreadyInsertedToAdminAndReturnsAdminIfIsNot(
            String cnpj,
            String studentId,
            Integer tenantId) {
        Admin admin = adminRepository.findByCnpj(cnpj, tenantId)
                .orElseThrow(() -> new RequestExceptions("Administrador " + cnpj + " não encontrado!"));
        for (Student student : admin.getStudents()) {
            var currentStudentId = student.getStudentId();
            if (currentStudentId.equals(studentId)) {
               throw new RequestExceptions(STUDENT_ALREADY_SAVED_TO_ADMINISTRATOR);
            }
        }
        return admin;
    }

    public Page<GetStudent> getAllStudentsFromDatabase(Pageable pageable, int tenantId) {
        Page<Student> allStudents = studentsRepository.findAll(pageable);

        Predicate<Student> filterByTenant = (student) -> {
            return student.getTenant() == tenantId;
        };

        Function<Student, GetStudent> mapToGetStudent = (student) -> {
            return new GetStudent(
                    student.getStudentId(),
                    student.getFirstName(),
                    student.getEmail(),
                    student.getStartDate(),
                    student.getAdmin(),
                    student.getLinks(),
                    student.getSkid(),
                    student.getSchoolClass());
        };

        List<GetStudent> studentsFilteredByTenantId =
                allStudents.stream()
                        .filter(filterByTenant)
                        .map(mapToGetStudent).collect(Collectors.toList());

        Collections.sort(studentsFilteredByTenantId);
        Page<GetStudent> page = new PageImpl<>(studentsFilteredByTenantId);
        return page;
    }

    private Student validateIfStudentExistsAndReturnIfExist(String studentSkId, int tenantId) {
        StudentValidation studentValidation = new StudentValidation();
        return studentValidation.validateIfStudentExistsAndReturnIfExist(studentsRepository, studentSkId, tenantId);
    }

    public GetStudent getStudentBySkId(String skid, int tenantId) {
        Student student = validateIfStudentExistsAndReturnIfExist(skid, tenantId);
        return new GetStudent(
                student.getStudentId(),
                student.getFirstName(),
                student.getEmail(),
                student.getStartDate(),
                student.getAdmin(),
                student.getLinks(),
                student.getSkid(),
                student.getSchoolClass()
        );
    }

    public void deleteStudent(String studentId, int tenantId) {
        Student student = validateIfStudentExistsAndReturnIfExist(studentId, tenantId);
        studentsRepository.delete(student);
    }
}
