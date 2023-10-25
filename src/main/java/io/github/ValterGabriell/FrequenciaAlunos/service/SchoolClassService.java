package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.controller.SchoolClassController;
import io.github.ValterGabriell.FrequenciaAlunos.domain.admins.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.domain.professors.Professor;
import io.github.ValterGabriell.FrequenciaAlunos.domain.school_class.SchoolClass;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.AdminRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.ProfessorRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.SchoolClassesRepository;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.PatternResponse;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.schoolclass.CreateSC;
import io.github.ValterGabriell.FrequenciaAlunos.util.GenerateSKId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class SchoolClassService {
    private final SchoolClassesRepository schoolClassesRepository;
    private final ProfessorRepository professorRepository;
    private final AdminRepository adminRepository;

    public SchoolClassService(SchoolClassesRepository schoolClassesRepository, ProfessorRepository professorRepository, AdminRepository adminRepository) {
        this.schoolClassesRepository = schoolClassesRepository;
        this.professorRepository = professorRepository;
        this.adminRepository = adminRepository;
    }

    @Transactional
    public PatternResponse<String> create(
            CreateSC createSC,
            String adminCnpj,
            int tenant) {

        SchoolClass sc = createSC.toSC();

        List<Professor> professorsBySkId =
                professorRepository.findAllBySkidInAndTenant(createSC.getProfessors(), tenant);
        List<Professor> professors = new ArrayList<>(professorsBySkId);

        Admin admin = adminRepository
                .findByCnpj(adminCnpj, tenant)
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

    public void delete(String skid, int tenant) {
        SchoolClass schoolClass = schoolClassesRepository.findBySkidAndTenant(
                skid,
                tenant
        ).orElseThrow(() -> new RequestExceptions("Sala não cadastrada!"));
        schoolClassesRepository.delete(schoolClass);
    }

}
