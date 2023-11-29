package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.controller.ProfessorController;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Professor;
import io.github.ValterGabriell.FrequenciaAlunos.helper.ROLES;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.AdminRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.ProfessorRepository;
import io.github.ValterGabriell.FrequenciaAlunos.dto.PatternResponse;
import io.github.ValterGabriell.FrequenciaAlunos.dto.professor.CreateProfessor;
import io.github.ValterGabriell.FrequenciaAlunos.dto.professor.ProfessorGet;
import io.github.ValterGabriell.FrequenciaAlunos.dto.professor.UpdateProfessor;
import io.github.ValterGabriell.FrequenciaAlunos.util.GenerateSKId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProfessorService {
    private final ProfessorRepository professorRepository;
    private final AdminRepository adminRepository;

    public ProfessorService(ProfessorRepository professorRepository, AdminRepository adminRepository) {
        this.professorRepository = professorRepository;
        this.adminRepository = adminRepository;
    }

    public PatternResponse<String> createProfessor(CreateProfessor createProfessor, String adminCnpj, int tenant) {
        Admin admin = adminRepository
                .findByCnpjAndTenant(adminCnpj, tenant)
                .orElseThrow(() -> new RequestExceptions("Administrador não encontrado"));

        Professor professor = createProfessor.toProfessor();
        professor.setTenant(tenant);
        professor.setSkid(GenerateSKId.generateSkId());

        professor.getContacts().forEach(contacts -> {
            contacts.setTenant(tenant);
            contacts.setUserId(professor.getSkid());
        });

        List<ROLES> roles = new ArrayList<>();
        roles.add(ROLES.PROFESSOR);
        professor.setRoles(roles);
        professor.setAdminId(adminCnpj);
        Professor professorSaved = professorRepository.save(professor);
        admin.getProfessors().add(professorSaved);
        adminRepository.save(admin);

        professorSaved
                .add(linkTo(methodOn(ProfessorController.class)
                        .getByIdentifier(professorSaved.getSkid(), tenant)).withSelfRel());

        return new PatternResponse<>(
                professorSaved.getSkid(),
                professorSaved.getLinks()
        );
    }

    public String updateProfessor(UpdateProfessor updateProfessor,
                                  int tenant,
                                  String skid) {
        Professor professor = professorRepository.findBySkidAndTenant(skid, tenant).orElseThrow(() -> {
            throw new RequestExceptions("Professor não encontrado! -> " + skid);
        });
        professor.setContacts(updateProfessor.getContacts());
        professor.setFirstName(updateProfessor.getFirstName());
        professor.setLastName(updateProfessor.getLastName());
        professor.setAverage(updateProfessor.getAverage());
        professor.setSchoolClasses(updateProfessor.getSchoolClasses());
        professor.setContacts(updateProfessor.getContacts());
        professor.setFinishedDate(updateProfessor.getFinishedDate());
        professor.setStartDate(updateProfessor.getStartDate());

        professor.getContacts().forEach(contacts -> {
            contacts.setTenant(tenant);
            contacts.setUserId(professor.getSkid());
        });

        Professor professorUpdated = professorRepository.save(professor);

        return "SKID: " + professorUpdated.getSkid();
    }

    public ProfessorGet getBySkId(int tenant, String skId) {
        Professor professor = professorRepository.findBySkidAndTenant(skId, tenant).orElseThrow(() -> {
            throw new RequestExceptions("Professor não encontrado! -> " + skId);
        });

        return professor.toGetProfessor();
    }

    public Page<ProfessorGet> getAllProfessors(int tenant, Pageable pageable) {
        Page<Professor> professorList = professorRepository.findAll(pageable);
        List<Professor> listaComTenantFiltrado =
                professorList.stream()
                        .filter(professor -> professor.getTenant() == tenant)
                        .toList();
        List<ProfessorGet> collect =
                listaComTenantFiltrado
                        .stream()
                        .map(professor -> professor
                                .add(linkTo(methodOn(ProfessorController.class)
                                        .getByIdentifier(professor.getSkid(), professor.getTenant()))
                                        .withSelfRel())
                                .toGetProfessor()
                        )
                        .toList();

        return new PageImpl<>(collect);
    }

    public void deleteProfessorByIdentifierNumber(int tenant, String skid) {
        Professor professor = professorRepository.findBySkidAndTenant(skid, tenant).orElseThrow(() -> {
            throw new RequestExceptions("Professor não encontrado! -> " + skid);
        });
        professorRepository.delete(professor);
    }
}
