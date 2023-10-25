package io.github.ValterGabriell.FrequenciaAlunos.validation;

import io.github.ValterGabriell.FrequenciaAlunos.domain.admins.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.AdminRepository;

public interface AdminValidation {
    Admin validateIfAdminExistsAndReturnIfExist_ByCnpj(
            AdminRepository adminRepository,
            String cnpj,
            Integer tenant);
    Admin validateIfAdminExistsAndReturnIfExist_BySkId(
            AdminRepository adminRepository,
            String skId,
            Integer tenant);
    void checkIfAdminTenantIdAlreadyExistsAndThrowAnExceptionIfItIs(AdminRepository adminRepository, int tenant);
}