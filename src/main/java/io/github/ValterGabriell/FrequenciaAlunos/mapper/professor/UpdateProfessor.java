package io.github.ValterGabriell.FrequenciaAlunos.mapper.professor;

import io.github.ValterGabriell.FrequenciaAlunos.domain.contacts.Contacts;
import io.github.ValterGabriell.FrequenciaAlunos.domain.professors.Professor;
import io.github.ValterGabriell.FrequenciaAlunos.domain.school_class.SchoolClass;

import java.time.LocalDateTime;
import java.util.List;

public class UpdateProfessor {
    private String skid;

    private String firstName;

    private String lastName;

    private Double average;

    private int tenant;

    private LocalDateTime startDate;
    private LocalDateTime finishedDate;
    private List<Contacts> contacts;
    private List<SchoolClass> schoolClasses;

    public UpdateProfessor() {
    }

    public UpdateProfessor(String skid, String firstName, String lastName, Double average, int tenant,
                           LocalDateTime startDate, LocalDateTime finishedDate, List<Contacts> contacts,
                           List<SchoolClass> schoolClasses) {
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

    public String getSkid() {
        return skid;
    }

    public void setSkid(String skid) {
        this.skid = skid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }


    public int getTenant() {
        return tenant;
    }

    public void setTenant(int tenant) {
        this.tenant = tenant;
    }

    public LocalDateTime getStartDate() {
        return startDate;
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

    public List<Contacts> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contacts> contacts) {
        this.contacts = contacts;
    }

    public List<SchoolClass> getSchoolClasses() {
        return schoolClasses;
    }

    public void setSchoolClasses(List<SchoolClass> schoolClasses) {
        this.schoolClasses = schoolClasses;
    }
    public Professor toProfessor() {
        Professor professor = new Professor();
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
