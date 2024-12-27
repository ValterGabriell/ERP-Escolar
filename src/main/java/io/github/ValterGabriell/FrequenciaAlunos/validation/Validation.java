package io.github.ValterGabriell.FrequenciaAlunos.validation;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Turma;
import io.github.ValterGabriell.FrequenciaAlunos.dto.admin.CreateNewAdmin;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.AdminRepository;

public abstract class Validation {
    public Admin validateIfAdminExistsAndReturnIfExistByCnpj(
            AdminRepository adminRepository,
            String cnpj,
            Integer tenant) {
        return null;
    }


    public boolean fieldContainsOnlyNumbers(String field) {
        return false;
    }



    public boolean validateIfIsNotEmpty(String field, String exceptionMessage) throws RequestExceptions {
        return false;
    }

}
