package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.controller.AdmController;
import io.github.ValterGabriell.FrequenciaAlunos.domain.admins.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.AdminRepository;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.admin.CreateNewAdmin;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.admin.GetAdminMapper;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.admin.UpdateAdminPassword;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.admin.UpdateAdminUsername;
import io.github.ValterGabriell.FrequenciaAlunos.util.GenerateSKId;
import io.github.ValterGabriell.FrequenciaAlunos.validation.Validation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Links;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class AdmService {
    private final AdminRepository adminRepository;

    public AdmService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    private Admin findAdminBySkIdOrThrowException(String skId) {
        Validation validation = new Validation();
        Admin admin = validation.validateIfAdminExistsAndReturnIfExist_BySkId(adminRepository, skId);
        if (admin == null) {
            throw new RequestExceptions(    "Usuário " + skId + " não encontrado!");
        } else {
            return admin;
        }
    }

    public String createNewAdmin(CreateNewAdmin newAdmin) {
        Validation validation = new Validation();
        boolean isPresent = validation.validateIfAdminExistsAndReturnIfExist_ByCnpj(adminRepository, newAdmin.getCnpj());
        if (isPresent) {
            throw new RequestExceptions("Cadastro com CNPJ encontrado!");
        } else {
            validation.checkIfAdminCnpjIsCorrect(newAdmin.getCnpj());
            Admin admin = newAdmin.toAdmin();
            admin.setSkId(GenerateSKId.generateSkId(admin.getId()));
            adminRepository.save(admin);
            return "Admin criado com sucesso! SkId: " + admin.getSkId();
        }
    }

    public Page<GetAdminMapper> getAllAdmins(Pageable pageable) {
        Page<Admin> adminList = adminRepository.findAll(pageable);


        List<GetAdminMapper> collect =
                adminList
                        .stream()
                        .map(admin -> admin
                                .add(linkTo(methodOn(AdmController.class)
                                        .getAdminBySkId(admin.getSkId())).withSelfRel())
                                .getAdminMapper()
                        )
                        .toList();

        Page<GetAdminMapper> page = new PageImpl<>(collect);
        return page;
    }

    public GetAdminMapper updateAdminUsername(String skId, UpdateAdminUsername updateAdminUsername) {
        Admin admin = findAdminBySkIdOrThrowException(skId);
        admin.setUsername(updateAdminUsername.getUsername());
        adminRepository.save(admin);
        return admin.getAdminMapper();
    }

    public GetAdminMapper updateAdminPassword(String skId, UpdateAdminPassword updateAdminPassword) {
        Admin admin = findAdminBySkIdOrThrowException(skId);
        admin.setPassword(updateAdminPassword.getPassword());
        adminRepository.save(admin);
        return admin.getAdminMapper();
    }

    public GetAdminMapper getAdminBySkId(String skId) {
        Admin admin = findAdminBySkIdOrThrowException(skId);

        admin.add(linkTo(
                methodOn(AdmController.class)
                        .getAllAdmins(Pageable.unpaged())).withRel("Lista de Administradores"));

        admin.add(linkTo(methodOn(AdmController.class)
                .updateUsername(admin.getSkId(), null)).withRel("Atualizar Username do Administrador"));

        admin.add(linkTo(methodOn(AdmController.class)
                .updatePassword(admin.getSkId(), null)).withRel("Atualizar Senha do Administrador"));

        admin.add(linkTo(methodOn(AdmController.class)
                .deleteAdminBySkId(admin.getSkId().substring(0,10).concat("..."))).withRel("Deletar o Administrador"));

        Links links = admin.getLinks();
        GetAdminMapper adminMapper = admin.getAdminMapper();
        adminMapper.setLinks(links);
        return adminMapper;
    }

    public String deleteAdminById(String skId) {
        Admin admin = findAdminBySkIdOrThrowException(skId);
        String response;
        if (admin != null) {
            adminRepository.deleteById(admin.getId());
            response = "Usuário " + skId + " deletado com sucesso!";
        } else {
            response = "Falha ao deletar o usuário " + skId;
        }
        return response;
    }
}
