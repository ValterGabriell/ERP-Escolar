package io.github.ValterGabriell.FrequenciaAlunos.mapper.discipline;

import io.github.ValterGabriell.FrequenciaAlunos.domain.discipline.Discipline;

public class CreateDiscipline {
    private String name;
    private String description;
    private String professorId;
    private String adminId;

    public CreateDiscipline(String name, String description, String professorId, String adminId) {
        this.name = name;
        this.description = description;
        this.professorId = professorId;
        this.adminId = adminId;
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

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }



    public Discipline toDiscipline() {
        return new Discipline(
                this.name,
                this.description,
                this.professorId,
                this.adminId
        );
    }
}
