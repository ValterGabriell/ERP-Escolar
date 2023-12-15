package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.controller.SchoolClassController;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Professor;
import io.github.ValterGabriell.FrequenciaAlunos.domain.SchoolClass;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Student;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.helper.PERIOD;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.AdminRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.ProfessorRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.SchoolClassesRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.StudentsRepository;
import io.github.ValterGabriell.FrequenciaAlunos.dto.PatternResponse;
import io.github.ValterGabriell.FrequenciaAlunos.dto.schoolclass.CreateSC;
import io.github.ValterGabriell.FrequenciaAlunos.util.GenerateSKId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class SchoolClassService {
    private final SchoolClassesRepository schoolClassesRepository;
    private final ProfessorRepository professorRepository;
    private final AdminRepository adminRepository;
    private final StudentsRepository studentsRepository;

    public SchoolClassService(SchoolClassesRepository schoolClassesRepository, ProfessorRepository professorRepository
            , AdminRepository adminRepository, StudentsRepository studentsRepository) {
        this.schoolClassesRepository = schoolClassesRepository;
        this.professorRepository = professorRepository;
        this.adminRepository = adminRepository;

        this.studentsRepository = studentsRepository;
    }

    @Transactional
    public PatternResponse<String> create(
            CreateSC createSC,
            String adminCnpj,
            int tenant) {

        boolean classWithSameNamePresent =
                schoolClassesRepository
                        .findAllByTenant(tenant)
                        .stream()
                        .anyMatch(schoolClass -> schoolClass.getName().equals(createSC.getName()));

        boolean match = Arrays.stream(PERIOD.values()).anyMatch(value -> value.getName().equals(createSC.getPeriod()));
        if (classWithSameNamePresent)
            throw new RequestExceptions("Classe já cadastrada", "set a different name");
        if (!match)
            throw new RequestExceptions("Sete um período válido", Arrays.toString(PERIOD.values()));


        SchoolClass sc = createSC.toSC();

        List<Professor> professorsBySkId =
                professorRepository.findAllBySkidInAndTenant(createSC.getProfessors(), tenant);
        List<Professor> professors = new ArrayList<>(professorsBySkId);

        Admin admin = adminRepository
                .findByCnpjAndTenant(adminCnpj, tenant)
                .orElseThrow(() -> new RequestExceptions("Administrador não encontrado"));

        sc.setProfessors(professors);
        sc.setAdminId(adminCnpj);
        sc.setTenant(tenant);
        sc.setSkid(GenerateSKId.generateSkId());

        SchoolClass saved = schoolClassesRepository.save(sc);

        admin.getSchoolClasses().add(saved);
        adminRepository.save(admin);

        saved.add(linkTo(methodOn(SchoolClassController.class).getBySkId(
                saved.getSkid(), tenant
        )).withSelfRel());

        return new PatternResponse<>(
                saved.getSkid(),
                saved.getLinks()
        );
    }

    public SchoolClass getBySkId(String skid, int tenant) {
        return schoolClassesRepository.findBySkidAndTenant(
                skid,
                tenant
        ).orElseThrow(() -> new RequestExceptions("Sala não cadastrada!"));
    }

    public List<SchoolClass> getAll(int tenant) {
        return schoolClassesRepository.findAllByTenant(
                tenant
        );
    }

    public List<Student> getAllStudentsByClassSkId(String skid, int tenant) {
        SchoolClass schoolClass = schoolClassesRepository.findBySkidAndTenant(
                skid,
                tenant
        ).orElseThrow(() -> new RequestExceptions("Sala não cadastrada!"));

        return schoolClass.getStudents();
    }

    public List<Professor> getAllProfessorsByClassSkId(String skid, int tenant) {
        SchoolClass schoolClass = schoolClassesRepository.findBySkidAndTenant(
                skid,
                tenant
        ).orElseThrow(() -> new RequestExceptions("Sala não cadastrada!"));

        return schoolClass.getProfessors();
    }


    public void delete(String skid, int tenant) {
        SchoolClass schoolClass = schoolClassesRepository.findBySkidAndTenant(
                skid,
                tenant
        ).orElseThrow(() -> new RequestExceptions("Sala não cadastrada!"));
        schoolClassesRepository.delete(schoolClass);
    }

    public PatternResponse<String> setStudentToClass(String classSkid, int tenant, String studentSkId) {
        SchoolClass schoolClass = schoolClassesRepository.findBySkidAndTenant(
                classSkid,
                tenant
        ).orElseThrow(() -> new RequestExceptions("Sala não cadastrada!"));

        Student student = studentsRepository.findBySkidAndTenant(studentSkId, tenant)
                .orElseThrow(() -> new RequestExceptions("Estudante não encontrado"));

        if (student.getSchoolClass().length() > 2)
            throw new RequestExceptions("Estudante já está cadastrado em uma classe"
                    , "Update Student Class. Check The Documentation");


        student.setSchoolClass(schoolClass.getSkid());
        studentsRepository.save(student);

        schoolClass.getStudents().add(student);
        SchoolClass saved = schoolClassesRepository.save(schoolClass);

        saved.add(linkTo(methodOn(SchoolClassController.class).getStudentsByClassSkId(classSkid, tenant)).withSelfRel());

        return new PatternResponse<String>(
                classSkid,
                saved.getLinks()
        );
    }

    public PatternResponse<String> setProfessorToClass(String classSkid, int tenant, String professorSkId) {
        SchoolClass schoolClass = schoolClassesRepository.findBySkidAndTenant(
                classSkid,
                tenant
        ).orElseThrow(() -> new RequestExceptions("Sala não cadastrada!"));

        Professor professor = professorRepository.findBySkidAndTenant(professorSkId, tenant)
                .orElseThrow(() -> new RequestExceptions("Estudante não encontrado"));

        if (professor.getSchoolClasses().contains(schoolClass))
            throw new RequestExceptions("Professor já está cadastrado nesta classe");


        professor.getSchoolClasses().add(schoolClass);
        professorRepository.save(professor);

        schoolClass.getProfessors().add(professor);
        SchoolClass saved = schoolClassesRepository.save(schoolClass);

        saved.add(linkTo(methodOn(SchoolClassController.class).getAllProfessorsByClassSkId(classSkid, tenant)).withSelfRel());

        return new PatternResponse<String>(
                classSkid,
                saved.getLinks()
        );
    }
}
