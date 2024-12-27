package io.github.ValterGabriell.FrequenciaAlunos.domain;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Entity(name = "tbl_estudantes")
public class Turma extends RepresentationModel<Turma> {

    @Id
    @Column(name = "turmaId", nullable = false)
    private String turmaId;

    @Column(name = "skid", nullable = false)
    private String skid;
    @Column(name = "nome", nullable = false)
    private String firstName;

    @Column(name = "email", nullable = false)
    private String email;


    @Column(name = "representante", nullable = false)
    private String representante;

    @Column(name = "startDate", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "finishedDate", nullable = true)
    private LocalDateTime finishedDate;

    @Column(name = "tenant", nullable = false)
    private Integer tenant;


    public Turma(
            String turmaId,
            String firstName,
            String email,
            LocalDateTime startDate,
            LocalDateTime finishedDate,
            Integer tenant) {
        this.turmaId = turmaId;
        this.firstName = firstName;
        this.email = email;
        this.startDate = startDate;
        this.finishedDate = finishedDate;
        this.tenant = tenant;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }


    public Turma() {
    }


    public String getTurmaId() {
        return turmaId;
    }

    public void setTurmaId(String id) {
        this.turmaId = id;
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


    public String getSkid() {
        return skid;
    }

    public void setSkid(String skid) {
        this.skid = skid;
    }


}
