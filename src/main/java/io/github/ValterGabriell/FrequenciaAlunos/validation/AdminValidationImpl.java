package io.github.ValterGabriell.FrequenciaAlunos.validation;

import io.github.ValterGabriell.FrequenciaAlunos.domain.admins.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.AdminRepository;

import java.util.Optional;

public class AdminValidationImpl implements AdminValidation{
    @Override
    public Admin validateIfAdminExistsAndReturnIfExist_ByCnpj(AdminRepository adminRepository, String cnpj, Integer tenant) {
        Optional<Admin> admin = adminRepository.findByCnpj(cnpj, tenant);
        return admin.orElse(null);
    }

    @Override
    public Admin validateIfAdminExistsAndReturnIfExist_BySkId(AdminRepository adminRepository, String skId, Integer tenant) {
        Optional<Admin> admin = adminRepository.findByCnpj(skId, tenant);
        return admin.orElse(null);
    }

    @Override
    public void checkIfAdminTenantIdAlreadyExistsAndThrowAnExceptionIfItIs(
            AdminRepository adminRepository,
            int tenant) {
        boolean adminWithTenantPresent = adminRepository.findByTenant(tenant).isPresent();
        if (adminWithTenantPresent){
            throw new RequestExceptions("Tenant já existente!");
        }
    }
}
