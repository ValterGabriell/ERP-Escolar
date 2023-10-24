package io.github.ValterGabriell.FrequenciaAlunos.mapper.frequency;

import io.github.ValterGabriell.FrequenciaAlunos.domain.days.Day;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.days.DaysList;

import java.util.ArrayList;
import java.util.List;

public class ResponseDaysThatStudentGoToClass {
    private String studentId;
    private List<DaysList> dayListThatStudentGoToClasses;

    public ResponseDaysThatStudentGoToClass() {
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public List<DaysList> getDaysListThatStudentGoToClass() {
        return dayListThatStudentGoToClasses;
    }

    public void setDaysListThatStudentGoToClass(List<Day> dayListThatStudentGoToClasses) {
        List<DaysList> daysLists = new ArrayList<>();
        dayListThatStudentGoToClasses.forEach(days -> {
            DaysList dayFormatted = days.toDaysList();
            daysLists.add(dayFormatted);
        });
        this.dayListThatStudentGoToClasses = daysLists;
    }
}