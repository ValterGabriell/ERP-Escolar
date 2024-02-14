package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.controller.AdmController;
import io.github.ValterGabriell.FrequenciaAlunos.controller.StudentsController;
import io.github.ValterGabriell.FrequenciaAlunos.domain.*;
import io.github.ValterGabriell.FrequenciaAlunos.dto.admin.*;
import io.github.ValterGabriell.FrequenciaAlunos.dto.professor.ProfessorGet;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.helper.ROLES;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.AdminRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.ApiKeyRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.ContactsRepository;
import io.github.ValterGabriell.FrequenciaAlunos.util.GenerateSKId;
import io.github.ValterGabriell.FrequenciaAlunos.util.GenerateTenant;
import io.github.ValterGabriell.FrequenciaAlunos.validation.AdminValidation;
import io.github.ValterGabriell.FrequenciaAlunos.validation.ContactValidation;
import io.github.ValterGabriell.FrequenciaAlunos.validation.FieldValidation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.Links;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class AdmService {
    private final AdminRepository adminRepository;
    private final ContactsRepository contactsRepository;
    private final ApiKeyRepository apiKeyRepository;
    private final AdminValidation adminValidation = new AdminValidation();
    private final ContactValidation contactValidation = new ContactValidation();
    private final FieldValidation fieldValidation = new FieldValidation();

    private final PasswordEncoder passwordEncoder;
    private final ModuleService moduleService;

    public AdmService(AdminRepository adminRepository, ContactsRepository contactsRepository, ApiKeyRepository apiKeyRepository, PasswordEncoder passwordEncoder, ModuleService moduleService) {
        this.adminRepository = adminRepository;
        this.contactsRepository = contactsRepository;
        this.apiKeyRepository = apiKeyRepository;
        this.passwordEncoder = passwordEncoder;
        this.moduleService = moduleService;
    }


    @Cacheable(value = "adminCache", key = "#tenant")
    public Admin getAdminByTenant(Integer tenant) {
        Optional<Admin> byTenant = adminRepository.findByTenant(tenant);
        if (byTenant.isEmpty()) throw new RequestExceptions("Tenant inválido");
        return byTenant.get();
    }

    private void checkIfCnpjAlreadyExistAndThrowAnErrorIfItIs(AdminRepository adminRepository, String cnpj) throws RequestExceptions {
        Optional<Admin> admin = adminRepository.findByCnpj(cnpj);
        if (admin.isPresent()) throw new RequestExceptions("CNPJ já cadastrado no sistema!");
    }

    private void validatingFieldsToCreateNewAdmin(CreateNewAdmin newAdmin) {
        String COMPLEMENT = " não pode estar vazio ou nulo!";
        fieldValidation.validateIfIsNotEmpty(newAdmin.getCnpj(), "CNPJ" + COMPLEMENT);
        fieldValidation.validateIfIsNotEmpty(newAdmin.getFirstName(), "First Name" + COMPLEMENT);
        fieldValidation.validateIfIsNotEmpty(newAdmin.getSecondName(), "Second Name" + COMPLEMENT);

        if (!fieldValidation.fieldContainsOnlyNumbers(newAdmin.getCnpj()))
            throw new RequestExceptions("CNPJ precisa conter apenas numeros");

        if (newAdmin.getModules().isEmpty())
            throw new RequestExceptions("Lista de módulos precisa ser fornecida");

        if (newAdmin.getContacts().isEmpty())
            throw new RequestExceptions("Contatos precisam ser fornecidos");
    }

    @Transactional
    public String createNewAdmin(CreateNewAdmin newAdmin) {
        validatingFieldsToCreateNewAdmin(newAdmin);
        checkIfCnpjAlreadyExistAndThrowAnErrorIfItIs(adminRepository, newAdmin.getCnpj());

        var tenant = generateTenant();
        newAdmin.getContacts().forEach(contact -> contactValidation
                .verifyIfEmailIsCorrectAndThrowAnErrorIfIsNot(contact.getEmail()));

        Admin admin = newAdmin.toAdmin();
        var contacts = setAdminIdAndTenantToContacts(tenant, admin);
        List<ROLES> roles = new ArrayList<>();
        roles.add(ROLES.ADMIN);
        admin.setTenant(tenant);
        admin.setSkId(GenerateSKId.generateSkId());
        admin.setRoles(roles);
        var encode = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encode);

        var modules = new ModulesEntity(tenant.toString(), newAdmin.getModules());

        contactsRepository.saveAll(contacts);
        adminRepository.save(admin);
        moduleService.insertModules(modules);


        admin.add(linkTo(methodOn(AdmController.class)
                .getAdminByCnpj(admin.getSkId(), 0)).withSelfRel());

        return "CNPJ: " + admin.getCnpj() + " TENANT: " + tenant;
    }

    private Integer generateTenant() {
        var tenant = GenerateTenant.generateTenant();
        boolean adminWithTenantPresent = adminRepository.findByTenant(tenant).isPresent();
        if (adminWithTenantPresent) {
            return generateTenant();
        }
        return tenant;
    }

    private static List<Contact> setAdminIdAndTenantToContacts(Integer tenant, Admin admin) {
        List<Contact> contacts = new ArrayList<>(admin.getContacts());
        contacts.forEach(contact -> {
            contact.setUserId(admin.getCnpj());
            contact.setTenant(tenant);
        });
        return contacts;
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
            moduleService.deleteModules(tenant);
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
                adminRepository.findByCnpjAndTenant(cnpj, tenant)
                        .orElseThrow(() -> new RequestExceptions("Admin não encontrado"));

        Function<Professor, ProfessorGet> professorProfessorGetFunction = (professor) -> new ProfessorGet(
                professor.getSkid(), professor.getFirstName(), professor.getLastName(), professor.getAverage(),
                professor.getIdentifierNumber(), professor.getTenant(), professor.getStartDate(),
                professor.getFinishedDate(), professor.getContacts()

        );
        return admin.getProfessors().stream().map(professorProfessorGetFunction).toList();
    }

    public LoginResponse loginAdmin(LoginDTO loginDTO) {
        Optional<Admin> admin = adminRepository.findByCnpj(loginDTO.getCnpj());

        boolean existsOnDatabase = admin.isPresent();
        if (!existsOnDatabase) throw new RequestExceptions("Admin não encontrado");

        if (!passwordEncoder.matches(loginDTO.getPassword(), admin.get().getPassword()))
            throw new RequestExceptions("Senha inválida");

        Integer tenant = admin.get().getTenant();
        Optional<ApiKeyEntity> apiKeyEntityFromDatabase = apiKeyRepository.findByTenant(tenant);
        ModulesEntity modules = moduleService.getModules(tenant);

        ApiKeyEntity keyEntity;
        boolean apiKeyIsValid = isApiKeyAdminValid(tenant);
        if (apiKeyEntityFromDatabase.isPresent()) {

            if (!apiKeyIsValid) {
                apiKeyRepository.delete(apiKeyEntityFromDatabase.get());
                keyEntity = generateNewApiKey(tenant);
                return new LoginResponse(keyEntity.getApiKey(), modules.getModules(), tenant);
            }

            return new LoginResponse(apiKeyEntityFromDatabase.get().getApiKey(), modules.getModules(), tenant);
        } else {
            keyEntity = generateNewApiKey(tenant);
            return new LoginResponse(keyEntity.getApiKey(), modules.getModules(), tenant);
        }
    }

    private ApiKeyEntity generateNewApiKey(Integer tenant) {
        ApiKeyEntity apiKeyEntity = new ApiKeyEntity(UUID.randomUUID().toString(), tenant, LocalDate.now());
        return apiKeyRepository.save(apiKeyEntity);
    }

    public void logoutUser(Integer tenant) {
        Optional<ApiKeyEntity> apiKey = apiKeyRepository.findByTenant(tenant);
        if (apiKey.isEmpty()) throw new RequestExceptions("Admin não encontrado");
        apiKeyRepository.delete(apiKey.get());
    }

    public boolean isApiKeyAdminValid(Integer tenant) {
        Optional<ApiKeyEntity> apiKey = apiKeyRepository.findByTenant(tenant);
        if (apiKey.isEmpty()) throw new RequestExceptions("Admin não encontrado");
        LocalDate expireDate = apiKey.get().getExpireDate();
        return LocalDate.now().isBefore(expireDate);
    }
}
