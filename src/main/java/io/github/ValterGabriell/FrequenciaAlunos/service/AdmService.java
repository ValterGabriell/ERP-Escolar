package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.domain.admins.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.excpetion.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.AdminRepository;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.admin.CreateNewAdmin;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.admin.GetAdmin;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdmService {
    private final AdminRepository adminRepository;

    public AdmService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    private Admin findAdminByIdOrThrowException(UUID adminId) {
        return adminRepository.findById(adminId)
                .orElseThrow(() -> new RequestExceptions("Usuário " + adminId + " não encontrado!"));
    }

    public String createNewAdmin(CreateNewAdmin newAdmin) {
        boolean adminIsPresentOnDatabase = adminRepository.findByEmail(newAdmin.getEmail()).isPresent();
        if (!adminIsPresentOnDatabase) {
            Admin admin = newAdmin.toAdmin();
            adminRepository.save(admin);
            return "Admin criado com sucesso!" + admin.getEmail();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já cadastrado!");
        }
    }

    public List<GetAdmin> getAllAdmins() {
        List<Admin> adminList = adminRepository.findAll();
        return adminList.stream().map(Admin::getAdminMapper).collect(Collectors.toList());
    }

    public GetAdmin updateAdminUsername(UUID adminId, String newUsername) {
        Admin admin = findAdminByIdOrThrowException(adminId);
        admin.setUsername(newUsername);
        adminRepository.save(admin);
        return admin.getAdminMapper();
    }

    public GetAdmin updateAdminPassword(UUID adminId, String newPassword) {
        Admin admin = findAdminByIdOrThrowException(adminId);
        admin.setPassword(newPassword);
        adminRepository.save(admin);
        return admin.getAdminMapper();
    }

    public GetAdmin getAdminById(UUID adminId) {
        return findAdminByIdOrThrowException(adminId).getAdminMapper();
    }

    public String deleteAdminById(UUID adminId) {
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
