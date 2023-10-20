package io.github.ValterGabriell.FrequenciaAlunos.domain.parents;

import io.github.ValterGabriell.FrequenciaAlunos.domain.contacts.Contacts;
import io.github.ValterGabriell.FrequenciaAlunos.domain.students.Student;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.parents.ParentGet;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Entity(name = "tbl_pais")
public class Parent extends RepresentationModel<Parent> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = true)
    private String skid;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String adminCnpj;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String identifierNumber;

    @Column(nullable = false)
    private Integer tenant;

    @OneToMany(targetEntity = Student.class, cascade = CascadeType.ALL)
    private List<Student> students;
    @OneToMany(targetEntity = Contacts.class, cascade = CascadeType.ALL)
    private List<Contacts> contacts;

    public Parent() {
    }

    public Parent(String firstName, String lastName, String identifierNumber, List<Contacts> contacts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.identifierNumber = identifierNumber;
        this.contacts = contacts;
    }

    public void setSkid(String skid) {
        this.skid = skid;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getSkid() {
        return skid;
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

    public Integer getTenant() {
        return tenant;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Contacts> getContacts() {
        return contacts;
    }

    public String getId() {
        return id;
    }

    public void setTenant(Integer tenant) {
        this.tenant = tenant;
    }

    public String getAdminCnpj() {
        return adminCnpj;
    }

    public void setAdminCnpj(String adminCnpj) {
        this.adminCnpj = adminCnpj;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setIdentifierNumber(String identifierNumber) {
        this.identifierNumber = identifierNumber;
    }

    public void setContacts(List<Contacts> contacts) {
        this.contacts = contacts;
    }

    public ParentGet toParentGet() {
        return new ParentGet(
                this.firstName,
                this.lastName,
                this.identifierNumber,
                this.contacts,
                this.students,
                this.getLinks()
        );
    }
}

