package io.github.ValterGabriell.FrequenciaAlunos.mapper.frequency;

import io.github.ValterGabriell.FrequenciaAlunos.domain.days.Day;
import io.github.ValterGabriell.FrequenciaAlunos.domain.students.Student;

import java.util.List;

public class FrequencyByClass {
    private Student student;
    private List<Day> dayList;

    public FrequencyByClass(Student student, List<Day> dayList) {
        this.student = student;
        this.dayList = dayList;
    }

    public Student getStudent() {
        return student;
    }

    public List<Day> getDayList() {
        return dayList;
    }
}
