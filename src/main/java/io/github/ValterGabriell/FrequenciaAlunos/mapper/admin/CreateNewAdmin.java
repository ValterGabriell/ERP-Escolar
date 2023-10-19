package io.github.ValterGabriell.FrequenciaAlunos.mapper.admin;

import io.github.ValterGabriell.FrequenciaAlunos.domain.admins.Admin;

public class CreateNewAdmin {

    private String firstName;
    private String secondName;
    private String password;
    private String email;
    private String cnpj;


    public CreateNewAdmin(String firstName, String password, String email, String cnpj, String secondName) {
        this.firstName = firstName;
        this.password = password;
        this.email = email;
        this.cnpj = cnpj;
        this.secondName = secondName;
    }

    public String getCnpj() {
        return cnpj;
    }


    public Admin toAdmin() {
        return new Admin(
                this.firstName,
                this.password,
                this.email,
                this.cnpj,
                this.secondName
        );
    }
}
