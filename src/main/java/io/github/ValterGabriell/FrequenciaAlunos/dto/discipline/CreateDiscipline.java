package io.github.ValterGabriell.FrequenciaAlunos.dto.discipline;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Disciplina;
import io.github.ValterGabriell.FrequenciaAlunos.helper.PERIOD;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CreateDiscipline {
    private String name;
    private String description;
    private String professorId;
    private String salaId;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private List<PERIOD> dias;
    private LocalTime horaInicio;
    private LocalTime horaFim;

    public CreateDiscipline(String name, String description, String professorId, String salaId,LocalDate dataInicio,LocalDate dataFim, List<PERIOD> dias,
                            LocalTime horaInicio,LocalTime horaFim) {
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



    public Disciplina toDiscipline() {
        return new Disciplina(
                this.name,
                this.description,
                this.professorId,
                this.salaId,
                this.dataInicio,
                this.dataInicio,
                this.dias,
                this.horaInicio,
                this.horaFim

        );
    }
}
