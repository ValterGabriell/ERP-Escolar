package io.github.ValterGabriell.FrequenciaAlunos.domain.discipline;

import jakarta.persistence.*;

@Entity(name = "tbl_disciplina")
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String skid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int tenant;

    @Column(nullable = false)
    private String professorId;

    public Discipline(String name, String description, int tenant, String professorId) {
        this.name = name;
        this.description = description;
        this.tenant = tenant;
        this.professorId = professorId;
    }

    public Discipline() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
