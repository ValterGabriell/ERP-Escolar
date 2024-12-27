package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.controller.SchoolClassController;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Professor;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Salas;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.AdminRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.ProfessorRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.SalasRepository;
import io.github.ValterGabriell.FrequenciaAlunos.dto.PatternResponse;
import io.github.ValterGabriell.FrequenciaAlunos.dto.schoolclass.CreateSC;
import io.github.ValterGabriell.FrequenciaAlunos.util.GenerateSKId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class SalasService {
    private final SalasRepository salasRepository;
    private final ProfessorRepository professorRepository;
    private final AdminRepository adminRepository;

    public SalasService(SalasRepository salasRepository, ProfessorRepository professorRepository
            , AdminRepository adminRepository) {
        this.salasRepository = salasRepository;
        this.professorRepository = professorRepository;
        this.adminRepository = adminRepository;
    }

    @Transactional
    public PatternResponse<String> create(
            CreateSC createSC,
            String adminCnpj,
            int tenant) {

        boolean classWithSameNamePresent =
                salasRepository
                        .findAllByTenant(tenant)
                        .stream()
                        .anyMatch(schoolClass -> schoolClass.getName().equals(createSC.getName()));
        if (classWithSameNamePresent)
            throw new RequestExceptions("Classe já cadastrada", "set a different name");


        Salas sc = createSC.toSC();


        Admin admin = adminRepository
                .findByCnpjAndTenant(adminCnpj, tenant)
                .orElseThrow(() -> new RequestExceptions("Administrador não encontrado"));
        sc.setAdminId(adminCnpj);
        sc.setTenant(tenant);
        sc.setSkid(GenerateSKId.generateSkId());

        Salas saved = salasRepository.save(sc);

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

    public Salas getBySkId(String skid, int tenant) {
        return salasRepository.findBySkidAndTenant(
                skid,
                tenant
        ).orElseThrow(() -> new RequestExceptions("Sala não cadastrada!"));
    }

    public List<Salas> getAll(int tenant) {
        return salasRepository.findAllByTenant(
                tenant
        );
    }


    public List<Professor> getAllProfessorsByClassSkId(String skid, int tenant) {
        Salas salas = salasRepository.findBySkidAndTenant(
                skid,
                tenant
        ).orElseThrow(() -> new RequestExceptions("Sala não cadastrada!"));

        return salas.getProfessors();
    }


    public void delete(String skid, int tenant) {
        Salas salas = salasRepository.findBySkidAndTenant(
                skid,
                tenant
        ).orElseThrow(() -> new RequestExceptions("Sala não cadastrada!"));
        salasRepository.delete(salas);
    }


    public PatternResponse<String> setProfessorToClass(String classSkid, int tenant, String professorSkId) {
        Salas salas = salasRepository.findBySkidAndTenant(
                classSkid,
                tenant
        ).orElseThrow(() -> new RequestExceptions("Sala não cadastrada!"));

        Professor professor = professorRepository.findBySkidAndTenant(professorSkId, tenant)
                .orElseThrow(() -> new RequestExceptions("Estudante não encontrado"));

        if (professor.getSchoolClasses().contains(salas))
            throw new RequestExceptions("Professor já está cadastrado nesta classe");


        professor.getSchoolClasses().add(salas);
        professorRepository.save(professor);

        salas.getProfessors().add(professor);
        Salas saved = salasRepository.save(salas);

        saved.add(linkTo(methodOn(SchoolClassController.class).getAllProfessorsByClassSkId(classSkid, tenant)).withSelfRel());

        return new PatternResponse<String>(
                classSkid,
                saved.getLinks()
        );
    }
}
