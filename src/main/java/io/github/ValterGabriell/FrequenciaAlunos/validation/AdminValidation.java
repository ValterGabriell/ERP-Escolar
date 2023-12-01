package io.github.ValterGabriell.FrequenciaAlunos.validation;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.AdminRepository;

import java.util.Optional;

public class AdminValidation extends Validation {
    @Override
    public Admin validateIfAdminExistsAndReturnIfExistByCnpj(AdminRepository adminRepository, String cnpj, Integer tenant) {
        Optional<Admin> admin = adminRepository.findByCnpjAndTenant(cnpj, tenant);
        return admin.orElse(null);
    }

    @Override
    public boolean validateIfAdminHasAlreadyTenant(AdminRepository adminRepository, String cnpj, int tenant) {
        return adminRepository.findByCnpjAndTenant(cnpj, tenant).isPresent();
    }
}
