package io.github.ValterGabriell.FrequenciaAlunos.domain.students;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Entity(name = "tbl_estudantes")
public class Student extends RepresentationModel<Student> {

    @Id
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "nome", nullable = false)
    private String firstName;

    @Column(name = "sobrenome", nullable = false)
    private String secondName;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "startDate", nullable = false)
    private LocalDateTime startDate;
    @Column(name = "finishedDate", nullable = true)
    private LocalDateTime finishedDate;

    @Column(name = "tenant", nullable = false)
    private Integer tenant;
    @Column(name = "admin_cnpj", nullable = false)
    private String admin;

    @Column(name = "school_class", nullable = false)
    private String schoolClass;

    public Student(
            String id,
            String firstName,
            String email,
            LocalDateTime startDate,
            LocalDateTime finishedDate,
            Integer tenant) {
        this.id = id;
        this.firstName = firstName;
        this.email = email;
        this.startDate = startDate;
        this.finishedDate = finishedDate;
        this.tenant = tenant;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getAdmin() {
        return admin;
    }

    public Student() {
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

    public String getEmail() {
        return email;
    }

    public void setFirstName(String username) {
        this.firstName = username;
    }


    public Integer getTenant() {
        return tenant;
    }

    public void setTenant(Integer tenant) {
        this.tenant = tenant;
    }
}
