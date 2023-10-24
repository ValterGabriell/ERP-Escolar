package io.github.ValterGabriell.FrequenciaAlunos.domain.professors;


import io.github.ValterGabriell.FrequenciaAlunos.domain.contacts.Contact;
import io.github.ValterGabriell.FrequenciaAlunos.helper.roles.ROLES;
import io.github.ValterGabriell.FrequenciaAlunos.domain.school_class.SchoolClass;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.professor.ProfessorGet;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "tbl_professores")
public class Professor extends RepresentationModel<Professor> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String professorId;

    @Column(nullable = false)
    private String skid;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Double average;

    @Column(nullable = false)
    private String password;

    @ManyToMany(mappedBy = "professors")
    private List<SchoolClass> schoolClasses;

    @Column(nullable = false)
    private int tenant;

    @Column(nullable = false)
    private String adminId;

    @Column(nullable = false)
    private List<ROLES> roles;

    @Column(name = "startDate", nullable = false)
    private LocalDateTime startDate;
    @Column(name = "finishedDate", nullable = true)
    private LocalDateTime finishedDate;

    @OneToMany(targetEntity = Contact.class, cascade = CascadeType.ALL)
    private List<Contact> contacts;

    public Professor() {
    }

    public Professor(String skid, String firstName, String lastName, String password, Double average, int tenant,
                     LocalDateTime startDate, LocalDateTime finishedDate,
                     List<Contact> contacts, List<SchoolClass> schoolClasses) {
        this.skid = skid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.average = average;
        this.tenant = tenant;
        this.startDate = startDate;
        this.finishedDate = finishedDate;
        this.contacts = contacts;
        this.schoolClasses = schoolClasses;
    }

    public void setProfessorId(String id) {
        this.professorId = id;
    }

    public void setSkid(String skid) {
        this.skid = skid;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public void setTenant(int tenant) {
        this.tenant = tenant;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setFinishedDate(LocalDateTime finishedDate) {
        this.finishedDate = finishedDate;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public void setSchoolClasses(List<SchoolClass> schoolClasses) {
        this.schoolClasses = schoolClasses;
    }

    public String getProfessorId() {
        return professorId;
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

    public Double getAverage() {
        return average;
    }


    public int getTenant() {
        return tenant;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getFinishedDate() {
        return finishedDate;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public List<SchoolClass> getSchoolClasses() {
        return schoolClasses;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
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

    public ProfessorGet toGetProfessor() {
        ProfessorGet professor = new ProfessorGet();
        professor.setSkid(this.skid);
        professor.setFirstName(this.firstName);
        professor.setLastName(this.lastName);
        professor.setAverage(this.average);
        professor.setTenant(this.tenant);
        professor.setStartDate(this.startDate);
        professor.setFinishedDate(this.finishedDate);
        professor.setContacts(this.contacts);
        professor.setSchoolClasses(this.schoolClasses);
        return professor;
    }
}
