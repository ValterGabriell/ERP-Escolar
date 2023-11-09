package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.controller.AdmController;
import io.github.ValterGabriell.FrequenciaAlunos.controller.StudentsController;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Contact;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Professor;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.helper.roles.ROLES;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.AdminRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.ContactsRepository;
import io.github.ValterGabriell.FrequenciaAlunos.dto.admin.*;
import io.github.ValterGabriell.FrequenciaAlunos.dto.professor.ProfessorGet;
import io.github.ValterGabriell.FrequenciaAlunos.util.GenerateSKId;
import io.github.ValterGabriell.FrequenciaAlunos.validation.AdminValidation;
import io.github.ValterGabriell.FrequenciaAlunos.validation.ContactValidation;
import io.github.ValterGabriell.FrequenciaAlunos.validation.FieldValidation;
import io.github.ValterGabriell.FrequenciaAlunos.validation.Validation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Links;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class AdmService {
    private final AdminRepository adminRepository;
    private final ContactsRepository contactsRepository;
    private final AdminValidation adminValidation = new AdminValidation();
    private final ContactValidation contactValidation = new ContactValidation();
    private final FieldValidation fieldValidation = new FieldValidation();

    private final PasswordEncoder passwordEncoder;

    public AdmService(AdminRepository adminRepository, ContactsRepository contactsRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.contactsRepository = contactsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private boolean returnIfAdminExistsOnDatabase(String cnpj, Integer tenant, Validation validation) {
        Admin adminIsPresent =
                validation.validateIfAdminExistsAndReturnIfExistByCnpj(adminRepository, cnpj, tenant);
        return adminIsPresent != null;
    }

    @Transactional
    public String createNewAdmin(CreateNewAdmin newAdmin, Integer tenant) {
        if (!fieldValidation.fieldContainsOnlyNumbers(newAdmin.getCnpj()))
            throw new RequestExceptions("CNPJ precisa conter apenas numeros");

        adminValidation.checkIfAdminTenantIdAlreadyExistsAndThrowAnExceptionIfItIs(adminRepository, tenant);

        newAdmin.getContacts().forEach(contact -> contactValidation
                .verifyIfEmailIsCorrectAndThrowAnErrorIfIsNot(contact.getEmail()));

        boolean adminExists =
                returnIfAdminExistsOnDatabase(newAdmin.getCnpj(), tenant, adminValidation);

        if (adminExists) {
            throw new RequestExceptions("Cadastro com CNPJ encontrado!");
        } else {
            Admin admin = newAdmin.toAdmin();

            List<Contact> contacts = setAdminIdAndTenantToContacts(tenant, admin);
            List<ROLES> roles = new ArrayList<>();
            roles.add(ROLES.ADMIN);
            roles.add(ROLES.PARENT);
            roles.add(ROLES.PROFESSOR);

            admin.setTenant(tenant);
            admin.setSkId(GenerateSKId.generateSkId());
            admin.setRoles(roles);
            String encode = passwordEncoder.encode(admin.getPassword());
            admin.setPassword(encode);

            contactsRepository.saveAll(contacts);
            adminRepository.save(admin);

            admin.add(linkTo(methodOn(AdmController.class)
                    .getAdminByCnpj(admin.getSkId(), 0)).withSelfRel());

            return "CNPJ: " + admin.getCnpj();
        }
    }

    private static List<Contact> setAdminIdAndTenantToContacts(Integer tenant, Admin admin) {
        List<Contact> contacts = new ArrayList<>(admin.getContacts());
        contacts.forEach(contact -> {
            contact.setUserId(admin.getCnpj());
            contact.setTenant(tenant);
        });
        return contacts;
    }

    public Page<GetAdminMapper> getAllAdmins(Pageable pageable) {
        Page<Admin> adminList = adminRepository.findAll(pageable);

        List<GetAdminMapper> collect =
                adminList
                        .stream()
                        .map(admin -> admin
                                .add(linkTo(methodOn(AdmController.class)
                                        .getAdminByCnpj(admin.getCnpj(), admin.getTenant())).withSelfRel())
                                .getAdminMapper()
                        )
                        .toList();

        Page<GetAdminMapper> page = new PageImpl<>(collect);
        return page;
    }


    @Transactional
    public GetAdminMapper updateAdminFirstName(String cnpj, UpdateAdminFirstName updateAdminFirstName, Integer tenant) {
        if (!fieldValidation.fieldContainsOnlyNumbers(cnpj))
            throw new RequestExceptions("CNPJ precisa conter apenas numeros");
        Admin admin = adminValidation.validateIfAdminExistsAndReturnIfExistByCnpj(adminRepository, cnpj, tenant);
        if (admin == null) {
            throw new RequestExceptions("Cadastro com CNPJ não encontrado!");
        }
        admin.setFirstName(updateAdminFirstName.getFirstName());
        adminRepository.save(admin);
        return admin.getAdminMapper();
    }

    public GetAdminMapper updateAdminSecondName(String cnpj, UpdateAdminSecondName updateAdminSecondName, Integer tenant) {
        if (!fieldValidation.fieldContainsOnlyNumbers(cnpj))
            throw new RequestExceptions("CNPJ precisa conter apenas numeros");
        Admin admin = adminValidation.validateIfAdminExistsAndReturnIfExistByCnpj(adminRepository, cnpj, tenant);
        if (admin == null) {
            throw new RequestExceptions("Cadastro com CNPJ não encontrado!");
        }
        admin.setSecondName(updateAdminSecondName.getSecondName());
        adminRepository.save(admin);
        return admin.getAdminMapper();
    }


    public GetAdminMapper updateAdminPassword(String cnpj, UpdateAdminPassword updateAdminPassword, Integer tenant) {
        if (!fieldValidation.fieldContainsOnlyNumbers(cnpj))
            throw new RequestExceptions("CNPJ precisa conter apenas numeros");
        Admin admin = adminValidation.validateIfAdminExistsAndReturnIfExistByCnpj(adminRepository, cnpj, tenant);
        if (admin == null) {
            throw new RequestExceptions("Cadastro com CNPJ não encontrado!");
        }
        admin.setPassword(updateAdminPassword.getPassword());
        adminRepository.save(admin);
        return admin.getAdminMapper();
    }


    public GetAdminMapper getAdminByCnpj(String cnpj, Integer tenant) {
        if (!fieldValidation.fieldContainsOnlyNumbers(cnpj))
            throw new RequestExceptions("CNPJ precisa conter apenas numeros");
        Admin admin = adminValidation.validateIfAdminExistsAndReturnIfExistByCnpj(adminRepository, cnpj, tenant);

        admin.add(linkTo(
                methodOn(AdmController.class)
                        .getAllAdmins(Pageable.unpaged())).withRel("Lista de Administradores"));

        admin.add(linkTo(methodOn(StudentsController.class)
                .insertStudentsIntoDatabase(null, admin.getCnpj(), admin.getTenant(), ""))
                .withRel("Inserir novo estudante"));


        Links links = admin.getLinks();
        GetAdminMapper adminMapper = admin.getAdminMapper();
        adminMapper.setLinks(links);
        return adminMapper;
    }

    public String deleteAdminByCnpj(String cnpj, Integer tenant) {
        if (!fieldValidation.fieldContainsOnlyNumbers(cnpj))
            throw new RequestExceptions("CNPJ precisa conter apenas numeros");
        Admin admin = adminValidation.validateIfAdminExistsAndReturnIfExistByCnpj(adminRepository, cnpj, tenant);
        String response;
        if (admin != null) {
            adminRepository.deleteById(admin.getAdminId());
            response = "Usuário " + cnpj + " deletado com sucesso!";
        } else {
            response = "Falha ao deletar o usuário: " + cnpj;
        }
        return response;
    }

    public List<ProfessorGet> getAllProfessorsByCnpj(String cnpj, int tenant) {
        if (!fieldValidation.fieldContainsOnlyNumbers(cnpj))
            throw new RequestExceptions("CNPJ precisa conter apenas numeros");
        Admin admin =
                adminRepository.findByCnpj(cnpj, tenant)
                        .orElseThrow(() -> new RequestExceptions("Admin não encontrado"));

        Function<Professor, ProfessorGet> professorProfessorGetFunction = (professor) -> new ProfessorGet(
                professor.getSkid(), professor.getFirstName(), professor.getLastName(), professor.getAverage(),
                professor.getIdentifierNumber(), professor.getTenant(), professor.getStartDate(),
                professor.getFinishedDate(), professor.getContacts()

        );
        return admin.getProfessors().stream().map(professorProfessorGetFunction).toList();
    }
}
