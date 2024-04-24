package io.github.ValterGabriell.FrequenciaAlunos.ANovo.ports.admin;

import io.github.ValterGabriell.FrequenciaAlunos.dto.admin.DtoCreateNewAdmin;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;

public interface IServiceAdminPort {
    String create(DtoCreateNewAdmin dtoCreateNewAdmin);

    void checkIfCnpjAlreadyExistAndThrowAnErrorIfItIs(String cnpj)
            throws RequestExceptions;

    boolean findByTenant(Integer tenant);
}
