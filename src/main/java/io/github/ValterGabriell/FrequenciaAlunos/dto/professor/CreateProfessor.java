package io.github.ValterGabriell.FrequenciaAlunos.dto.professor;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Professor;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class CreateProfessor {

    private String firstName;

    private String lastName;

    private String identifierNumber;

    private String password;

    private Double average;

    private int tenant;

    private LocalDateTime startDate;
    private LocalDateTime finishedDate;


    public CreateProfessor() {
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


    public String getIdentifierNumber() {
        return identifierNumber;
    }

    public void setIdentifierNumber(String identifierNumber) {
        this.identifierNumber = identifierNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }


    public Professor toProfessor() {
        Professor professor = new Professor();
        professor.setFirstName(this.firstName);
        professor.setLastName(this.lastName);
        professor.setIdentifierNumber(this.identifierNumber);
        professor.setAverage(this.average);
        professor.setTenant(this.tenant);
        professor.setStartDate(this.startDate);
        professor.setFinishedDate(this.finishedDate);

        professor.setSchoolClasses(new ArrayList<>());
        professor.setPassword(this.password);
        return professor;
    }

}
