package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Roles;
import io.github.ValterGabriell.FrequenciaAlunos.domain.admins.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.domain.admins.dto.GetAdmin;
import io.github.ValterGabriell.FrequenciaAlunos.excpetion.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdmService {
    private final AdminRepository adminRepository;

    public AdmService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    private Admin findAdminById(UUID adminId) {
        return adminRepository.findById(adminId).orElseThrow(() -> new RequestExceptions("Usuário não encontrado!"));
    }

    public String createNewAdmin(Admin insertAdmin) {
        insertAdmin.setId(UUID.randomUUID());
        insertAdmin.setRoles(List.of(Roles.ADMIN));
        adminRepository.save(insertAdmin);
        return "Admin criado com sucesso!" + insertAdmin.getEmail();
    }

    public List<GetAdmin> getAllAdmins() {
        List<Admin> adminList = adminRepository.findAll();
        return adminList.stream().map(Admin::toDTOGet).collect(Collectors.toList());
    }

    public GetAdmin updateAdminUsername(UUID adminId, String newUsername) {
        Admin admin = findAdminById(adminId);
        admin.setUsername(newUsername);
        adminRepository.save(admin);
        return admin.toDTOGet();
    }

    public GetAdmin updateAdminPassword(UUID adminId, String newPassword) {
        Admin admin = findAdminById(adminId);
        admin.setPassword(newPassword);
        adminRepository.save(admin);
        return admin.toDTOGet();
    }

    public GetAdmin getAdminById(UUID adminId){
        Admin admin = findAdminById(adminId);
        return admin.toDTOGet();
    }

    public String deleteAdminById(UUID adminId){
        Admin admin = findAdminById(adminId);
        String response;
        if (admin != null){
            adminRepository.deleteById(adminId);
            response = "Usuário " + adminId + " deletado com sucesso!";
        }else{
            response = "Falha ao deletar o usuário " + adminId;
        }
        return response;
    }
}
