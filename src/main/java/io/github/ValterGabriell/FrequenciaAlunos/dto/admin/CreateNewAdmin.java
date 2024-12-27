package io.github.ValterGabriell.FrequenciaAlunos.dto.admin;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.dto.contacs.CreateContact;
import io.github.ValterGabriell.FrequenciaAlunos.helper.MODULE;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CreateNewAdmin {

    private String firstName;
    private String password;
    private String cnpj;



    public CreateNewAdmin(
            String firstName,
            String password,
            String cnpj,
            List<CreateContact> contacts,
            List<MODULE> modules
    ) {
        this.firstName = firstName;
        this.password = password;
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }



    public Admin toAdmin() {

        return new Admin(
                this.firstName,
                this.password,
                this.cnpj,
                "-"
        );
    }

    public String getFirstName() {
        return firstName;
    }


    public String getPassword() {
        return password;
    }

}
