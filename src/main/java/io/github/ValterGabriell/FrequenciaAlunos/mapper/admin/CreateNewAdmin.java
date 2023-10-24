package io.github.ValterGabriell.FrequenciaAlunos.mapper.admin;

import io.github.ValterGabriell.FrequenciaAlunos.domain.admins.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.domain.contacts.Contact;

import java.util.List;

public class CreateNewAdmin {

    private String firstName;
    private String secondName;
    private String password;
    private String email;
    private String cnpj;
    private List<Contact> contacts;
    public CreateNewAdmin(
            String firstName,
            String password,
            String email,
            String cnpj,
            String secondName,
            List<Contact> contacts
    ) {
        this.firstName = firstName;
        this.password = password;
        this.email = email;
        this.cnpj = cnpj;
        this.secondName = secondName;
        this.contacts = contacts;
    }

    public String getCnpj() {
        return cnpj;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public Admin toAdmin() {
        return new Admin(
                this.firstName,
                this.password,
                this.email,
                this.cnpj,
                this.secondName,
                this.contacts
        );
    }
}
