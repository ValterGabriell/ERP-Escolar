package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.domain.admins.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.AdminRepository;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.admin.CreateNewAdmin;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.admin.GetAdmin;
import io.github.ValterGabriell.FrequenciaAlunos.validation.Validation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdmService {
    private final AdminRepository adminRepository;

    public AdmService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    private Admin findAdminByIdOrThrowException(String adminId) {
        Validation validation = new Validation();
        Admin admin = validation.validateIfAdminExistsAndReturnIfExist_ById(adminRepository, adminId);
        if (admin == null) {
            throw new RequestExceptions("Usuário " + adminId + " não encontrado!");
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
            Admin admin = newAdmin.toAdmin();
            adminRepository.save(admin);
            return "Admin criado com sucesso!" + admin.getEmail();
        }
    }

    public Page<GetAdmin> getAllAdmins(Pageable pageable) {
        Page<Admin> adminList = adminRepository.findAll(pageable);
        List<GetAdmin> collect = adminList.stream().map(Admin::getAdminMapper).collect(Collectors.toList());
        Page<GetAdmin> page = new PageImpl<>(collect);
        return page;
    }

    public GetAdmin updateAdminUsername(String adminId, String newUsername) {
        Admin admin = findAdminByIdOrThrowException(adminId);
        admin.setUsername(newUsername);
        adminRepository.save(admin);
        return admin.getAdminMapper();
    }

    public GetAdmin updateAdminPassword(String adminId, String newPassword) {
        Admin admin = findAdminByIdOrThrowException(adminId);
        admin.setPassword(newPassword);
        adminRepository.save(admin);
        return admin.getAdminMapper();
    }

    public GetAdmin getAdminById(String adminId) {
        return findAdminByIdOrThrowException(adminId).getAdminMapper();
    }

    public String deleteAdminById(String adminId) {
        Admin admin = findAdminByIdOrThrowException(adminId);
        String response;
        if (admin != null) {
            adminRepository.deleteById(adminId);
            response = "Usuário " + adminId + " deletado com sucesso!";
        } else {
            response = "Falha ao deletar o usuário " + adminId;
        }
        return response;
    }
}
