package io.github.ValterGabriell.FrequenciaAlunos.validation;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.dto.admin.CreateNewAdmin;
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

    @Override
    public void validatingFieldsToCreateNewAdmin(CreateNewAdmin newAdmin) {
        String COMPLEMENT = " não pode estar vazio ou nulo!";
        validateIfIsNotEmpty(newAdmin.getCnpj(), "CNPJ" + COMPLEMENT);
        validateIfIsNotEmpty(newAdmin.getFirstName(), "First Name" + COMPLEMENT);
        validateIfIsNotEmpty(newAdmin.getSecondName(), "Second Name" + COMPLEMENT);

        if (!fieldContainsOnlyNumbers(newAdmin.getCnpj()))
            throw new RequestExceptions("CNPJ precisa conter apenas numeros");

        if (newAdmin.getModules().isEmpty())
            throw new RequestExceptions("Lista de módulos precisa ser fornecida");

        if (newAdmin.getContacts().isEmpty())
            throw new RequestExceptions("Contatos precisam ser fornecidos");
    }
}
