package io.github.ValterGabriell.FrequenciaAlunos.domain.professors;


import io.github.ValterGabriell.FrequenciaAlunos.domain.contacts.Contacts;
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
    private String id;

    @Column(nullable = false)
    private String skid;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Double average;


    @Column(nullable = false)
    private int tenant;

    @Column(name = "startDate", nullable = false)
    private LocalDateTime startDate;
    @Column(name = "finishedDate", nullable = true)
    private LocalDateTime finishedDate;

    @OneToMany(targetEntity = Contacts.class, cascade = CascadeType.ALL)
    private List<Contacts> contacts;

    @ManyToMany(targetEntity = SchoolClass.class)
    private List<SchoolClass> schoolClasses;

    public Professor() {
    }

    public Professor(String skid, String firstName, String lastName, Double average, int tenant,
                     LocalDateTime startDate, LocalDateTime finishedDate,
                     List<Contacts> contacts, List<SchoolClass> schoolClasses) {
        this.skid = skid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.average = average;
        this.tenant = tenant;
        this.startDate = startDate;
        this.finishedDate = finishedDate;
        this.contacts = contacts;
        this.schoolClasses = schoolClasses;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setContacts(List<Contacts> contacts) {
        this.contacts = contacts;
    }

    public void setSchoolClasses(List<SchoolClass> schoolClasses) {
        this.schoolClasses = schoolClasses;
    }

    public String getId() {
        return id;
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

    public List<Contacts> getContacts() {
        return contacts;
    }

    public List<SchoolClass> getSchoolClasses() {
        return schoolClasses;
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
