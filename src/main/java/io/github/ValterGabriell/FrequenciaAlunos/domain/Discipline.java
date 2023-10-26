package io.github.ValterGabriell.FrequenciaAlunos.domain;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

@Entity(name = "tbl_disciplina")
public class Discipline extends RepresentationModel<Discipline> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String disciplineId;

    @Column(nullable = false)
    private String skid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int tenant;

    @Column(nullable = false)
    private String adminId;

    @Column(nullable = false)
    private String professorId;

    public Discipline(String name, String description, String professorId,String adminId) {
        this.name = name;
        this.description = description;
        this.professorId = professorId;
        this.adminId = adminId;
    }

    public Discipline() {
    }

    public String getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(String id) {
        this.disciplineId = id;
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

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
}
