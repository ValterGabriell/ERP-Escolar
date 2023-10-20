package io.github.ValterGabriell.FrequenciaAlunos.mapper.parents;

import io.github.ValterGabriell.FrequenciaAlunos.domain.contacts.Contacts;
import io.github.ValterGabriell.FrequenciaAlunos.domain.parents.Parent;

import java.util.List;


public class CreateParent {
    private String firstName;
    private String lastName;
    private String identifierNumber;
    private List<Contacts> contacts;


    public CreateParent() {
    }

    public CreateParent(String firstName, String lastName, String identifierNumber, List<Contacts> contacts) {
        this.firstName = firstName;
        this.lastName = lastName;
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


    public List<Contacts> getContacts() {
        return contacts;
    }

    public Parent toParent() {
        return new Parent(
                this.firstName,
                this.lastName,
                this.identifierNumber,
                this.contacts
        );
    }
}

