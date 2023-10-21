package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.controller.ParentController;
import io.github.ValterGabriell.FrequenciaAlunos.domain.login.Login;
import io.github.ValterGabriell.FrequenciaAlunos.domain.parents.Parent;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.LoginRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.ParentsRepository;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.parents.CreateParent;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.parents.ParentGet;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.parents.UpdateParent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ParentsService {
    private final ParentsRepository parentsRepository;
    private final LoginRepository loginRepository;

    public ParentsService(ParentsRepository parentsRepository, LoginRepository loginRepository) {
        this.parentsRepository = parentsRepository;
        this.loginRepository = loginRepository;
    }

    private Login createLogin(Parent parent, int tenant) {
        // Cria um objeto 'Login' para o fluxo de login
        Login login = new Login(
                parent.getContacts().get(0).getEmail(),
                parent.getIdentifierNumber(),
                tenant
        );

        // Salva o objeto 'Login' no repositório
        Login loginSaved = loginRepository.save(login);

        // Define o campo 'skid' do 'Login' com o ID gerado após a primeira inserção
        loginSaved.setSkid(loginSaved.getId());

        // Salva o objeto 'Login' atualizado com o 'skid' no repositório novamente
        return loginRepository.save(loginSaved);
    }


    public String createParent(CreateParent createParent, int tenant, String adminCnpj) {

        boolean isPresent =
                parentsRepository.findByIdentifierNumberAndTenant(createParent.getIdentifierNumber(), tenant).isPresent();

        if (isPresent) {
            throw new RequestExceptions("Genitor já cadastrado! -> " + createParent.getIdentifierNumber());
        }

        Parent parent = createParent.toParent();
        parent.setTenant(tenant);
        parent.setAdminCnpj(adminCnpj);
        parent.setSkid(" ");

        parent.getContacts().forEach(contacts -> {
            contacts.setTenant(tenant);
            contacts.setUserId(parent.getIdentifierNumber());
        });


        Parent parentSaved = parentsRepository.save(parent);
        parentSaved.setSkid(parentSaved.getId());
        Parent updated = parentsRepository.save(parentSaved);
        createLogin(parent,tenant);
        return updated.getIdentifierNumber();
    }

    public String updateParentFirstName(UpdateParent updateParent, int tenant, String adminCnpj, String identifierNumber) {
        Parent parent = parentsRepository.findByIdentifierNumberAndTenant(identifierNumber, tenant).orElseThrow(() -> {
            throw new RequestExceptions("Genitor não encontrado! -> " + identifierNumber);
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
        return parentUpdated.getIdentifierNumber();
    }

    public ParentGet getByIdentifierNumber(int tenant, String identifierNumber) {
        Parent parent = parentsRepository.findByIdentifierNumberAndTenant(identifierNumber, tenant).orElseThrow(() -> {
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
        Parent parent = parentsRepository.findByIdentifierNumberAndTenant(identifierNumber, tenant).orElseThrow(() -> {
            throw new RequestExceptions("Genitor não encontrado! -> " + identifierNumber);
        });
        parentsRepository.delete(parent);
    }

}
