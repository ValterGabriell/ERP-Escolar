package io.github.ValterGabriell.FrequenciaAlunos.domain.admins;

import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.AdminRepository;

public interface AdminValidation {
    boolean validateIfAdminExistsAndReturnIfExist_ByCnpj(
            AdminRepository adminRepository,
            String cnpj,
            Integer tenant);
    Admin validateIfAdminExistsAndReturnIfExist_BySkId(
            AdminRepository adminRepository,
            String skId,
            Integer tenant);

    void checkIfAdminCnpjIsCorrect(String cnpj);
}
