package io.github.ValterGabriell.FrequenciaAlunos.domain.admins;

import io.github.ValterGabriell.FrequenciaAlunos.domain.contacts.Contacts;
import io.github.ValterGabriell.FrequenciaAlunos.domain.professors.Professors;
import io.github.ValterGabriell.FrequenciaAlunos.domain.school_class.SchoolClass;
import io.github.ValterGabriell.FrequenciaAlunos.domain.students.Student;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.admin.GetAdminMapper;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Entity(name = "tbl_admin")
public class Admin extends RepresentationModel<Admin> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = true)
    private String skid;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String secondName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String cnpj;

    @Column(nullable = false)
    private Integer tenant;

    @Column(nullable = false)
    private String loginId;

    @OneToMany(targetEntity = Student.class, cascade = CascadeType.ALL)
    private List<Student> students;

    @OneToMany(targetEntity = SchoolClass.class, cascade = CascadeType.ALL)
    private List<SchoolClass> schoolClasses;

    @OneToMany(targetEntity = Contacts.class, cascade = CascadeType.ALL)
    @Column(nullable = false)
    private List<Contacts> contacts;

    @OneToMany(targetEntity = Professors.class, cascade = CascadeType.ALL)
    private List<Professors> professors;


    public Admin(
            String firstName,
            String password,
            String email,
            String cnpj,
            String secondName,
            List<Contacts> contacts
    ) {
        this.firstName = firstName;
        this.password = password;
        this.email = email;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEmail() {
        return email;
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

    public String getLoginId() {
        return loginId;
    }

    public List<Contacts> getContacts() {
        return contacts;
    }


    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public GetAdminMapper getAdminMapper() {
        return new GetAdminMapper(
               getCnpj(),
                getSkId(),
                getFirstName(),
                getSecondName(),
                getEmail(),
                getContacts(),
                getLoginId(),
                getLinks()
        );
    }
}
