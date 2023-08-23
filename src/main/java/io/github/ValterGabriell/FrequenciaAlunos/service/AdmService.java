package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Roles;
import io.github.ValterGabriell.FrequenciaAlunos.domain.admins.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.domain.admins.dto.GetAdmin;
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

    public String createNewAdmin(Admin insertAdmin) {
        insertAdmin.setId(UUID.randomUUID());
        insertAdmin.setRoles(List.of(Roles.ADMIN));
        adminRepository.save(insertAdmin);
        return "Admin criado com sucesso!" + insertAdmin.getEmail();
    }


    public List<GetAdmin> getAllAdmins(){
        List<Admin> adminList = adminRepository.findAll();
        return adminList.stream().map(Admin::toDTOGet).collect(Collectors.toList());
    }
}
