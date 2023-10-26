package io.github.ValterGabriell.FrequenciaAlunos.mapper.admin;

import io.github.ValterGabriell.FrequenciaAlunos.domain.admins.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.domain.contacts.Contact;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.contacs.CreateContact;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CreateNewAdmin {

    private String firstName;
    private String secondName;
    private String password;
    private String email;
    private String cnpj;
    private List<CreateContact> contacts;

    public CreateNewAdmin(
            String firstName,
            String password,
            String email,
            String cnpj,
            String secondName,
            List<CreateContact> contacts
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
                this.email,
                this.cnpj,
                this.secondName,
                collect
        );
    }
}
