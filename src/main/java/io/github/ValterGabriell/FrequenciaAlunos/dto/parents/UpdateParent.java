package io.github.ValterGabriell.FrequenciaAlunos.dto.parents;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Contact;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Parent;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Student;

import java.util.List;

public class UpdateParent {
    private String firstName;
    private String lastName;
    private String password;
    private String identifierNumber;
    private List<Contact> contacts;
    private List<Student> students;

    public UpdateParent(String firstName, String lastName, String password, String identifierNumber, List<Contact> contacts, List<Student> students) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.identifierNumber = identifierNumber;
        this.contacts = contacts;
        this.students = students;
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

    public List<Student> getStudents() {
        return students;
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
