package io.github.ValterGabriell.FrequenciaAlunos.domain;

import io.github.ValterGabriell.FrequenciaAlunos.helper.PERIOD;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity(name = "tbl_disciplina")
public class Disciplina extends RepresentationModel<Disciplina> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID disciplineId;

    @Column(nullable = false)
    private String skid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int tenant;

    @Column(nullable = false)
    private String salaId;

    @Column(nullable = false)
    private String professorId;

    @Column(nullable = false)
    private LocalDate dataInicio;

    @Column(nullable = false)
    private LocalDate dataFim;
    @ElementCollection
    @Column(nullable = false)
    private List<PERIOD> dias;

    @Column(nullable = false)
    private LocalTime horaInicio;

    @Column(nullable = false)
    private LocalTime horaFim;
    public Disciplina
            (String name, String description,
             String professorId, String salaId,LocalDate dataInicio,LocalDate dataFim, List<PERIOD> dias,
             LocalTime horaInicio,LocalTime horaFim ) {
        this.name = name;
        this.description = description;
        this.professorId = professorId;
        this.salaId = salaId;
        this.dataFim = dataFim;
        this.dataInicio = dataInicio;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.dias = dias;
    }


    // Getters e Setters
    public UUID getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(UUID disciplineId) {
        this.disciplineId = disciplineId;
    }

    public String getSkid() {
        return skid;
    }

    public void setSkid(String skid) {
        this.skid = skid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTenant() {
        return tenant;
    }

    public void setTenant(int tenant) {
        this.tenant = tenant;
    }

    public String getProfessorId() {
        return professorId;
    }

    public void setProfessorId(String professorId) {
        this.professorId = professorId;
    }

    public String getSalaId() {
        return salaId;
    }

    public void setSalaId(String salaId) {
        this.salaId = salaId;
    }
}
