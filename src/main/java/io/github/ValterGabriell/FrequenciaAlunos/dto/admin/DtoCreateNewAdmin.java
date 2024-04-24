package io.github.ValterGabriell.FrequenciaAlunos.dto.admin;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Contact;
import io.github.ValterGabriell.FrequenciaAlunos.dto.contacs.CreateContact;
import io.github.ValterGabriell.FrequenciaAlunos.helper.MODULE;

import java.util.List;
import java.util.function.Function;

public class DtoCreateNewAdmin {

    private String firstName;
    private String secondName;
    private String password;
    private String cnpj;
    private List<CreateContact> contacts;
    private List<MODULE> modules;

    public DtoCreateNewAdmin(
            String firstName,
            String password,
            String cnpj,
            String secondName,
            List<CreateContact> contacts,
            List<MODULE> modules
    ) {
        this.firstName = firstName;
        this.password = password;
        this.cnpj = cnpj;
        this.secondName = secondName;
        this.contacts = contacts;
        this.modules = modules;
    }

    public List<MODULE> getModules() {
        return modules;
    }

    public String getCnpj() {
        return cnpj;
    }

    public List<CreateContact> getContacts() {
        return contacts;
    }

    public Admin toAdmin() {

        Function<CreateContact, Contact>
                createContactContactFunction = (val) -> {
            return new Contact(val.getPhone(), val.getEmail(), "", -1);
        };

        List<Contact> collect = this.contacts.stream().map(createContactContactFunction).toList();
        return new Admin(
                this.firstName,
                this.password,
                this.cnpj,
                this.secondName,
                collect
        );
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getPassword() {
        return password;
    }



}
