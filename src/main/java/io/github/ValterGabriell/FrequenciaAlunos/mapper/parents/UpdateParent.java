package io.github.ValterGabriell.FrequenciaAlunos.mapper.parents;

import io.github.ValterGabriell.FrequenciaAlunos.domain.contacts.Contacts;
import io.github.ValterGabriell.FrequenciaAlunos.domain.parents.Parent;
import io.github.ValterGabriell.FrequenciaAlunos.domain.students.Student;

import java.util.List;

public class UpdateParent {
    private String firstName;
    private String lastName;
    private String identifierNumber;
    private List<Contacts> contacts;
    private List<Student> students;

    public UpdateParent(String firstName, String lastName, String identifierNumber, List<Contacts> contacts, List<Student> students) {
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

    public List<Contacts> getContacts() {
        return contacts;
    }

    public List<Student> getStudents() {
        return students;
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
