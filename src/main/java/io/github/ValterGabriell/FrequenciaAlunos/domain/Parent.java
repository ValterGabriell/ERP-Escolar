package io.github.ValterGabriell.FrequenciaAlunos.domain;

import io.github.ValterGabriell.FrequenciaAlunos.helper.roles.ROLES;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.parents.ParentGet;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Entity(name = "tbl_pais")
public class Parent extends RepresentationModel<Parent> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String parentId;

    @Column(nullable = true)
    private String skid;

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String adminCnpj;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String identifierNumber;

    @Column(nullable = false)
    private Integer tenant;

    @Column(nullable = false)
    private List<ROLES> roles;

    @OneToMany(targetEntity = Student.class, cascade = CascadeType.ALL)
    private List<Student> students;
    @OneToMany(targetEntity = Contact.class, cascade = CascadeType.ALL)
    private List<Contact> contacts;

    public Parent() {
    }

    public Parent(String firstName, String lastName, String password,String identifierNumber, List<Contact> contacts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
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

    public List<Contact> getContacts() {
        return contacts;
    }

    public String getParentId() {
        return parentId;
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

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public ParentGet toParentGet() {
        return new ParentGet(
                this.firstName,
                this.lastName,
                this.identifierNumber,
                this.contacts,
                this.students,
                this.getLinks(),
                this.skid
        );
    }

    public List<ROLES> getRoles() {
        return roles;
    }

    public void setRoles(List<ROLES> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

