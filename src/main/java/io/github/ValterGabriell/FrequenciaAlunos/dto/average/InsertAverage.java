package io.github.ValterGabriell.FrequenciaAlunos.dto.average;

public class InsertAverage {
    private String studentSkId;
    private String disciplineSkId;
    private Double average;

    private int evaluation;

    public InsertAverage(String studentSkId, String disciplineSkId, Double average, int evaluation) {
        this.studentSkId = studentSkId;
        this.disciplineSkId = disciplineSkId;
        this.average = average;
        this.evaluation = evaluation;
    }

    public String getStudentSkId() {
        return studentSkId;
    }

    public String getDisciplineSkId() {
        return disciplineSkId;
    }

    public Double getAverage() {
        return average;
    }

    public int getEvaluation() {
        return evaluation;
    }
}
