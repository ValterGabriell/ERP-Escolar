package io.github.ValterGabriell.FrequenciaAlunos.domain.admins;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Roles;
import io.github.ValterGabriell.FrequenciaAlunos.domain.students.Student;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.admin.GetAdmin;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity(name = "table_admin")
public class Admin {
    @Id
    private String id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String cnpj;

    @Column(nullable = false)
    private List<Roles> roles;

    @OneToMany(targetEntity = Student.class, cascade = CascadeType.ALL)
    private List<Student> students;


    public Admin(String id, String username, String password, String email, String cnpj) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.cnpj = cnpj;
        this.roles = List.of(Roles.ADMIN);
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCpf(String cpf) {
        this.cnpj = cpf;
    }

    public GetAdmin getAdminMapper() {
        return new GetAdmin(
                getId(),
                getUsername(),
                getEmail(),
                getCnpj(),
                getRoles()
        );
    }


}
