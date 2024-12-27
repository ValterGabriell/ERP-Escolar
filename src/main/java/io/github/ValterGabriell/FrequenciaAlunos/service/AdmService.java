package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.controller.AdmController;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Professor;
import io.github.ValterGabriell.FrequenciaAlunos.dto.admin.*;
import io.github.ValterGabriell.FrequenciaAlunos.dto.professor.ProfessorGet;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.helper.ROLES;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.AdminRepository;
import io.github.ValterGabriell.FrequenciaAlunos.util.GenerateSKId;
import io.github.ValterGabriell.FrequenciaAlunos.util.GenerateTenant;
import io.github.ValterGabriell.FrequenciaAlunos.util.PasswordEncoder;
import io.github.ValterGabriell.FrequenciaAlunos.validation.AdminValidation;
import io.github.ValterGabriell.FrequenciaAlunos.validation.FieldValidation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.Links;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class AdmService {
    private final AdminRepository adminRepository;
    private final AdminValidation adminValidation = new AdminValidation();
    private final FieldValidation fieldValidation = new FieldValidation();

    private final PasswordEncoder passwordEncoder;


    public AdmService(AdminRepository adminRepository,  PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
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

        if (!fieldValidation.fieldContainsOnlyNumbers(newAdmin.getCnpj()))
            throw new RequestExceptions("CNPJ precisa conter apenas numeros");
    }

    @Transactional
    public String createNewAdmin(CreateNewAdmin newAdmin) {
        validatingFieldsToCreateNewAdmin(newAdmin);
        checkIfCnpjAlreadyExistAndThrowAnErrorIfItIs(adminRepository, newAdmin.getCnpj());

        var tenant = generateTenant();
        Admin admin = newAdmin.toAdmin();
        List<ROLES> roles = new ArrayList<>();
        roles.add(ROLES.ADMIN);
        admin.setTenant(tenant);
        admin.setSkId(GenerateSKId.generateSkId());
        admin.setRoles(roles);
        var encode = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encode);
        adminRepository.save(admin);
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
                adminRepository.findByCnpjAndTenant(cnpj, tenant)
                        .orElseThrow(() -> new RequestExceptions("Admin não encontrado"));

        Function<Professor, ProfessorGet> professorProfessorGetFunction = (professor) -> new ProfessorGet(
                professor.getSkid(), professor.getFirstName(), professor.getLastName(), professor.getAverage(),
                professor.getIdentifierNumber(), professor.getTenant(), professor.getStartDate(),
                professor.getFinishedDate()

        );
        return admin.getProfessors().stream().map(professorProfessorGetFunction).toList();
    }

    public LoginResponse loginAdmin(LoginDTO loginDTO) {
        Optional<Admin> admin = adminRepository.findByCnpj(loginDTO.getCnpj());

        boolean existsOnDatabase = admin.isPresent();
        if (!existsOnDatabase) throw new RequestExceptions("Admin não encontrado");

        if (!passwordEncoder.matches(loginDTO.getPassword(), admin.get().getPassword()))
            throw new RequestExceptions("Senha inválida");
        return new LoginResponse(admin.get().getTenant());
    }
}
