package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.controller.ParentController;
import io.github.ValterGabriell.FrequenciaAlunos.domain.parents.Parent;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.ParentsRepository;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.parents.CreateParent;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.parents.ParentGet;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.parents.UpdateParent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ParentsService {
    private final ParentsRepository parentsRepository;

    public ParentsService(ParentsRepository parentsRepository) {
        this.parentsRepository = parentsRepository;
    }


    public String createParent(CreateParent createParent, int tenant, String adminCnpj) {
        Parent parent = createParent.toParent();
        parent.setTenant(tenant);
        parent.setAdminCnpj(adminCnpj);
        Parent parentSaved = parentsRepository.save(parent);
        parentSaved.setSkid(parentSaved.getId());
        Parent updated = parentsRepository.save(parentSaved);
        return updated.getIdentifierNumber();
    }

    public String updateParentFirstName(UpdateParent updateParent, int tenant, String adminCnpj, String identifierNumber) {
        Parent parent = parentsRepository.findByIdentifierNumber(identifierNumber, tenant).orElseThrow(() -> {
            throw new RequestExceptions("Genitor não encontrado! -> " + identifierNumber);
        });
        parent.setContacts(updateParent.getContacts());
        parent.setFirstName(updateParent.getFirstName());
        parent.setLastName(updateParent.getLastName());
        parent.setIdentifierNumber(updateParent.getIdentifierNumber());
        parent.setStudents(updateParent.getStudents());
        parent.setContacts(updateParent.getContacts());

        Parent parentUpdated = parentsRepository.save(parent);
        return parentUpdated.getIdentifierNumber();
    }

    public ParentGet getByIdentifierNumber(int tenant, String identifierNumber) {
        Parent parent = parentsRepository.findByIdentifierNumber(identifierNumber, tenant).orElseThrow(() -> {
            throw new RequestExceptions("Genitor não encontrado! -> " + identifierNumber);
        });

        return new ParentGet(
                parent.getFirstName(),
                parent.getLastName(),
                parent.getIdentifierNumber(),
                parent.getContacts(),
                parent.getStudents(),
                parent.getLinks()
        );
    }

    public Page<ParentGet> getAllParents(int tenant, Pageable pageable) {
        Page<Parent> adminList = parentsRepository.findAll(pageable);
        List<Parent> listaComTenantFiltrado =
                adminList.stream()
                        .filter(parent -> parent.getTenant() == tenant).toList();
        List<ParentGet> collect =
                listaComTenantFiltrado
                        .stream()
                        .map(parent -> parent
                                .add(linkTo(methodOn(ParentController.class)
                                        .getParentByIdentifier(parent.getIdentifierNumber(),
                                                parent.getTenant())).withSelfRel())
                                .toParentGet()
                        )
                        .toList();

        return new PageImpl<>(collect);
    }

    public void deleteParentByIdentifierNumber(int tenant, String identifierNumber) {
        Parent parent = parentsRepository.findByIdentifierNumber(identifierNumber, tenant).orElseThrow(() -> {
            throw new RequestExceptions("Genitor não encontrado! -> " + identifierNumber);
        });
        parentsRepository.delete(parent);
    }

}
