package io.github.ValterGabriell.FrequenciaAlunos.domain;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Entity(name = "tbl_estudantes")
public class Student extends RepresentationModel<Student> {

    @Id
    @Column(name = "studentId", nullable = false)
    private String studentId;

    @Column(name = "skid", nullable = false)
    private String skid;
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
            String studentId,
            String firstName,
            String email,
            LocalDateTime startDate,
            LocalDateTime finishedDate,
            Integer tenant) {
        this.studentId = studentId;
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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String id) {
        this.studentId = id;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(LocalDateTime finishedDate) {
        this.finishedDate = finishedDate;
    }

    public void setTenant(Integer tenant) {
        this.tenant = tenant;
    }

    public String getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(String schoolClass) {
        this.schoolClass = schoolClass;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getSkid() {
        return skid;
    }

    public void setSkid(String skid) {
        this.skid = skid;
    }


}
