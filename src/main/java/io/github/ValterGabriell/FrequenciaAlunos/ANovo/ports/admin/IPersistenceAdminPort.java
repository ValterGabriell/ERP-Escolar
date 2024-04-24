package io.github.ValterGabriell.FrequenciaAlunos.ANovo.ports.admin;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.dto.admin.DtoCreateNewAdmin;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;

import java.util.Optional;

public interface IPersistenceAdminPort {
    String create(DtoCreateNewAdmin dtoCreateNewAdmin);

    void checkIfCnpjAlreadyExistAndThrowAnErrorIfItIs(String cnpj)
            throws RequestExceptions;

    boolean findByTenant(Integer tenant);
}
