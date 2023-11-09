package io.github.ValterGabriell.FrequenciaAlunos.dto.professor;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Contact;
import io.github.ValterGabriell.FrequenciaAlunos.domain.SchoolClass;

import java.time.LocalDateTime;
import java.util.List;

public class UpdateProfessor {
    private String firstName;

    private String lastName;

    private Double average;

    private LocalDateTime startDate;
    private LocalDateTime finishedDate;
    private List<Contact> contacts;
    private List<SchoolClass> schoolClasses;

    public UpdateProfessor() {
    }

    public UpdateProfessor(String firstName, String lastName, Double average,
                           LocalDateTime startDate, LocalDateTime finishedDate, List<Contact> contacts,
                           List<SchoolClass> schoolClasses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.average = average;
        this.startDate = startDate;
        this.finishedDate = finishedDate;
        this.contacts = contacts;
        this.schoolClasses = schoolClasses;
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

    public void setSchoolClasses(List<SchoolClass> schoolClasses) {
        this.schoolClasses = schoolClasses;
    }

}
