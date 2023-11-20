package io.github.ValterGabriell.FrequenciaAlunos.domain;

import io.github.ValterGabriell.FrequenciaAlunos.helper.roles.ROLES;
import io.github.ValterGabriell.FrequenciaAlunos.dto.admin.GetAdminMapper;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Entity(name = "tbl_admin")
public class Admin extends RepresentationModel<Admin> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String adminId;
    @Column(nullable = true)
    private String skid;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String secondName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String cnpj;

    @Column(nullable = false)
    private Integer tenant;
    @Column(nullable = false)
    private List<ROLES> roles;

    @OneToMany(targetEntity = Student.class, cascade = CascadeType.ALL)
    private List<Student> students;

    @OneToMany(targetEntity = SchoolClass.class, cascade = CascadeType.ALL)
    private List<SchoolClass> schoolClasses;

    @OneToMany(targetEntity = Contact.class, cascade = CascadeType.ALL)
    @Column(nullable = false)
    private List<Contact> contacts;

    @OneToMany(targetEntity = Professor.class, cascade = CascadeType.ALL)
    private List<Professor> professors;


    public Admin(
            String firstName,
            String password,
            String cnpj,
            String secondName,
            List<Contact> contacts
    ) {
        this.firstName = firstName;
        this.password = password;
        this.cnpj = cnpj;
        this.secondName = secondName;
        this.contacts = contacts;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Admin() {
    }

    public String getAdminId() {
        return adminId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String username) {
        this.firstName = username;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getCnpj() {
        return cnpj;
    }

    public Integer getTenant() {
        return tenant;
    }

    public String getSkId() {
        return skid;
    }

    public void setSkId(String skId) {
        this.skid = skId;
    }

    public void setTenant(Integer tenant) {
        this.tenant = tenant;
    }


    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getSecondName() {
        return secondName;
    }


    public List<Contact> getContacts() {
        return contacts;
    }


    public String getPassword() {
        return password;
    }

    public List<SchoolClass> getSchoolClasses() {
        return schoolClasses;
    }

    public List<Professor> getProfessors() {
        return professors;
    }

    public void setProfessors(List<Professor> professors) {
        this.professors = professors;
    }

    public String getSkid() {
        return skid;
    }

    public List<ROLES> getRoles() {
        return roles;
    }

    public GetAdminMapper getAdminMapper() {
        return new GetAdminMapper(
                getCnpj(),
                getSkId(),
                getFirstName(),
                getSecondName(),
                getContacts(),
                getLinks()
        );
    }

    public void setRoles(List<ROLES> roles) {
        this.roles = roles;
    }
}
