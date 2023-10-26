package io.github.ValterGabriell.FrequenciaAlunos.domain.average;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity(name = "tbl_notas_alunos")
public class Average {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String averageId;
    @Column(nullable = false)
    private String studentSkId;

    @Column(nullable = false)
    private String disciplineSkId;

    @Column(nullable = false)
    private String skid;

    @Column(nullable = false)
    private Double average;

    @Column(nullable = false)
    private int evaluation;

    @Column(nullable = false)
    private Integer tenant;

    public Average(String studentSkId, String disciplineSkId, Double average, int evaluation) {
        this.averageId = UUID.randomUUID().toString();
        this.studentSkId = studentSkId;
        this.disciplineSkId = disciplineSkId;
        this.average = average;
        this.evaluation = evaluation;
    }

    public Average() {
    }

    public String getAverageId() {
        return averageId;
    }

    public void setAverageId(String averageId) {
        this.averageId = averageId;
    }

    public String getStudentSkId() {
        return studentSkId;
    }

    public void setStudentSkId(String studentId) {
        this.studentSkId = studentId;
    }

    public String getDisciplineSkId() {
        return disciplineSkId;
    }

    public void setDisciplineSkId(String disciplineId) {
        this.disciplineSkId = disciplineId;
    }

    public String getSkid() {
        return skid;
    }

    public void setSkid(String skid) {
        this.skid = skid;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    public Integer getTenant() {
        return tenant;
    }

    public void setTenant(Integer tenant) {
        this.tenant = tenant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Average average1 = (Average) o;
        return evaluation == average1.evaluation && Objects.equals(studentSkId, average1.studentSkId) && Objects.equals(disciplineSkId, average1.disciplineSkId) && Objects.equals(average, average1.average) && Objects.equals(tenant, average1.tenant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentSkId, disciplineSkId, average, evaluation, tenant);
    }
}
