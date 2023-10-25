package io.github.ValterGabriell.FrequenciaAlunos.mapper.parents;

import io.github.ValterGabriell.FrequenciaAlunos.domain.contacts.Contact;
import io.github.ValterGabriell.FrequenciaAlunos.domain.students.Student;
import org.springframework.hateoas.Links;

import java.util.List;

public class ParentGet {
    private String firstName;
    private String lastName;
    private String identifierNumber;

    private String skid;
    private List<Contact> contacts;
    private List<Student> students;
    private Links links;

    public ParentGet(String firstName, String lastName, String identifierNumber, List<Contact> contacts,
                     List<Student> students, Links links,String skid) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.identifierNumber = identifierNumber;
        this.contacts = contacts;
        this.students = students;
        this.links = links;
        this.skid = skid;
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

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public String getSkid() {
        return skid;
    }

    public void setSkid(String skid) {
        this.skid = skid;
    }
}
