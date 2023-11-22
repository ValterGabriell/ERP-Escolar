package io.github.ValterGabriell.FrequenciaAlunos.domain;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.UUID;

@Entity(name = "tbl_turmas")
public class SchoolClass extends RepresentationModel<SchoolClass> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID classId;

    @Column(nullable = false)
    private String skid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String secondName;

    @Column(nullable = false)
    private String period;

    @Column(name = "year_school",nullable = false)
    private int year;

    @Column(nullable = false)
    private Integer tenant;

    @Column(nullable = false)
    private String adminId;
    @OneToMany(targetEntity = Student.class, cascade = CascadeType.ALL)
    private List<Student> students;

    @OneToMany(targetEntity = Discipline.class, cascade = CascadeType.ALL)
    private List<Discipline> disciplines;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "class_has_professor", joinColumns =
            {@JoinColumn(name = "classId")}, inverseJoinColumns = {
            @JoinColumn(name = "professorId")
    })
    private List<Professor> professors;

    public SchoolClass() {
    }

    public SchoolClass(String name, String secondName, String period, int year) {
        this.name = name;
        this.secondName = secondName;
        this.period = period;
        this.year = year;
    }

    public String getSkid() {
        return skid;
    }

    public void setSkid(String skid) {
        this.skid = skid;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(List<Discipline> disciplines) {
        this.disciplines = disciplines;
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

    public String getSecondName() {
        return secondName;
    }

    public String getPeriod() {
        return period;
    }

    public int getYear() {
        return year;
    }
}
