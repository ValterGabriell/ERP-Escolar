package io.github.ValterGabriell.FrequenciaAlunos.dto.frequency;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Student;

import java.util.List;

public class FrequencyByDayDTO {
    private List<Student> studentNotJustified;
    private List<Student> studentJustified;
    private String date;

    public FrequencyByDayDTO() {
    }


    public FrequencyByDayDTO(List<Student> studentNotJustified, List<Student> studentJustified, String date) {
        this.studentNotJustified = studentNotJustified;
        this.studentJustified = studentJustified;
        this.date = date;
    }

    public List<Student> getStudentJustified() {
        return studentJustified;
    }

    public List<Student> getStudentNotJustified() {
        return studentNotJustified;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
