package io.github.ValterGabriell.FrequenciaAlunos.domain.admins;

import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.AdminRepository;

public interface AdminValidation {
    boolean validateIfAdminExistsAndReturnIfExist_ByCnpj(AdminRepository adminRepository, String cnpj);
    Admin validateIfAdminExistsAndReturnIfExist_ById(AdminRepository adminRepository, String adminId);
}
