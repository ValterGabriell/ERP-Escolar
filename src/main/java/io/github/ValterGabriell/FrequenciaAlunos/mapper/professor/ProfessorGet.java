package io.github.ValterGabriell.FrequenciaAlunos.mapper.professor;

import io.github.ValterGabriell.FrequenciaAlunos.domain.contacts.Contact;
import io.github.ValterGabriell.FrequenciaAlunos.domain.professors.Professor;
import io.github.ValterGabriell.FrequenciaAlunos.domain.school_class.SchoolClass;

import java.time.LocalDateTime;
import java.util.List;

public class ProfessorGet {
    private String skid;

    private String firstName;

    private String lastName;

    private Double average;

    private String identifierNumber;

    private int tenant;

    private LocalDateTime startDate;
    private LocalDateTime finishedDate;
    private List<Contact> contacts;
    private List<SchoolClass> schoolClasses;

    public ProfessorGet() {
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

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<SchoolClass> getSchoolClasses() {
        return schoolClasses;
    }

    public String getIdentifierNumber() {
        return identifierNumber;
    }

    public void setIdentifierNumber(String identifierNumber) {
        this.identifierNumber = identifierNumber;
    }

    public void setSchoolClasses(List<SchoolClass> schoolClasses) {
        this.schoolClasses = schoolClasses;
    }
    public Professor toProfessor() {
        Professor professor = new Professor();
        professor.setSkid(this.skid);
        professor.setIdentifierNumber(this.identifierNumber);
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