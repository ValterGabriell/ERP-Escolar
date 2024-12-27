package io.github.ValterGabriell.FrequenciaAlunos.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.UUID;

@Entity(name = "tbl_turmas")
public class Salas extends RepresentationModel<Salas> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID classId;

    @Column(nullable = false)
    private String skid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer tenant;

    @Column(nullable = false)
    private String adminId;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "class_has_professor", joinColumns =
            {@JoinColumn(name = "classId")}, inverseJoinColumns = {
            @JoinColumn(name = "professorId")
    })
    private List<Professor> professors;

    public Salas() {
    }

    public Salas(String name) {
        this.name = name;
    }

    public String getSkid() {
        return skid;
    }

    public void setSkid(String skid) {
        this.skid = skid;
    }

    public Integer getTenant() {
        return tenant;
    }

    public void setTenant(Integer tenant) {
        this.tenant = tenant;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public List<Professor> getProfessors() {
        return professors;
    }

    public void setProfessors(List<Professor> professors) {
        this.professors = professors;
    }

    public UUID getClassId() {
        return classId;
    }

    public String getName() {
        return name;
    }

}
