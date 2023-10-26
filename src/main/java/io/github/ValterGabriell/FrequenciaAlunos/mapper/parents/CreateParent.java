package io.github.ValterGabriell.FrequenciaAlunos.mapper.parents;

import io.github.ValterGabriell.FrequenciaAlunos.domain.contacts.Contact;
import io.github.ValterGabriell.FrequenciaAlunos.domain.parents.Parent;

import java.util.List;


public class CreateParent {
    private String firstName;
    private String lastName;
    private String password;
    private String identifierNumber;
    private List<Contact> contacts;


    public CreateParent() {
    }

    public CreateParent(String firstName, String lastName, String password, String identifierNumber, List<Contact> contacts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.identifierNumber = identifierNumber;
        this.contacts = contacts;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getIdentifierNumber() {
        return identifierNumber;
    }


    public List<Contact> getContacts() {
        return contacts;
    }

    public String getPassword() {
        return password;
    }

    public Parent toParent() {
        return new Parent(
                this.firstName,
                this.lastName,
                this.password,
                this.identifierNumber,
                this.contacts
        );
    }
}

