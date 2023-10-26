package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.controller.ParentController;
import io.github.ValterGabriell.FrequenciaAlunos.domain.parents.Parent;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.helper.roles.ROLES;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.ParentsRepository;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.PatternResponse;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.parents.CreateParent;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.parents.ParentGet;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.parents.UpdateParent;
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
public class ParentsService {
    private final ParentsRepository parentsRepository;

    public ParentsService(ParentsRepository parentsRepository) {
        this.parentsRepository = parentsRepository;
    }

    public PatternResponse<String> createParent(CreateParent createParent, int tenant, String adminCnpj) {

        boolean isPresent =
                parentsRepository.findByIdentifierNumberAndTenant(createParent.getIdentifierNumber(), tenant).isPresent();

        if (isPresent) {
            throw new RequestExceptions("Genitor já cadastrado! -> " + createParent.getIdentifierNumber());
        }

        Parent parent = createParent.toParent();
        parent.setTenant(tenant);
        parent.setAdminCnpj(adminCnpj);

        parent.getContacts().forEach(contacts -> {
            contacts.setTenant(tenant);
            contacts.setUserId(parent.getIdentifierNumber());
        });

        List<ROLES> roles = new ArrayList<>();
        roles.add(ROLES.PROFESSOR);
        parent.setRoles(roles);
        parent.setSkid(GenerateSKId.generateSkId());
        Parent parentSaved = parentsRepository.save(parent);
        parentSaved.add(linkTo(methodOn(ParentController.class)
                .getBySkId(parentSaved.getSkid(), 0)).withSelfRel());

        return new PatternResponse<>(
                parentSaved.getSkid(),
                parentSaved.getLinks()
        );
    }

    public String updateParentFirstName(UpdateParent updateParent, int tenant, String adminCnpj, String skid) {
        Parent parent = parentsRepository.findBySkidAndTenant(skid, tenant).orElseThrow(() -> {
            throw new RequestExceptions("Genitor não encontrado! -> " + skid);
        });
        parent.setContacts(updateParent.getContacts());
        parent.setFirstName(updateParent.getFirstName());
        parent.setLastName(updateParent.getLastName());
        parent.setIdentifierNumber(updateParent.getIdentifierNumber());
        parent.setStudents(updateParent.getStudents());
        parent.setContacts(updateParent.getContacts());

        parent.getContacts().forEach(contacts -> {
            contacts.setTenant(tenant);
            contacts.setUserId(parent.getIdentifierNumber());
        });

        Parent parentUpdated = parentsRepository.save(parent);
        return "SKID: " + parentUpdated.getSkid();
    }

    public ParentGet getBySkId(int tenant, String skid) {
        Parent parent = parentsRepository.findBySkidAndTenant(skid, tenant).orElseThrow(() -> {
            throw new RequestExceptions("Genitor não encontrado! -> " + skid);
        });

        return new ParentGet(
                parent.getFirstName(),
                parent.getLastName(),
                parent.getIdentifierNumber(),
                parent.getContacts(),
                parent.getStudents(),
                parent.getLinks(),
                parent.getSkid()
        );
    }

    public Page<ParentGet> getAllParents(int tenant, Pageable pageable) {
        Page<Parent> adminList = parentsRepository.findAll(pageable);
        List<ParentGet> listaComTenantFiltrado =
                adminList.stream()
                        .filter(parent -> parent.getTenant() == tenant)
                        .map(item -> item.toParentGet())
                        .toList();
        return new PageImpl<>(listaComTenantFiltrado);
    }

    public void deleteParentBySkId(int tenant, String skid) {
        Parent parent = parentsRepository.findBySkidAndTenant(skid, tenant).orElseThrow(() -> {
            throw new RequestExceptions("Genitor não encontrado! -> " + skid);
        });
        parentsRepository.delete(parent);
    }

}
