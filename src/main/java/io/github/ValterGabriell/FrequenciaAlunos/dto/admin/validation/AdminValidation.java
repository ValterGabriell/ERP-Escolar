package io.github.ValterGabriell.FrequenciaAlunos.dto.admin.validation;

import io.github.ValterGabriell.FrequenciaAlunos.dto.admin.DtoCreateNewAdmin;
import io.github.ValterGabriell.FrequenciaAlunos.dto.contacs.CreateContact;
import io.github.ValterGabriell.FrequenciaAlunos.helper.MODULE;

import java.util.List;

public class AdminValidation extends DtoCreateNewAdmin {
    public AdminValidation(String firstName, String password, String cnpj, String secondName, List<CreateContact> contacts, List<MODULE> modules) {
        super(firstName, password, cnpj, secondName, contacts, modules);
    }

    public boolean validate(){
        return false;
    }
}
