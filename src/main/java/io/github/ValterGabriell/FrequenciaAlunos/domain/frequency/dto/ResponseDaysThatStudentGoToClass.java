package io.github.ValterGabriell.FrequenciaAlunos.domain.frequency.dto;

import io.github.ValterGabriell.FrequenciaAlunos.domain.days.Days;
import io.github.ValterGabriell.FrequenciaAlunos.domain.days.dto.DaysList;

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

    public void setDaysListThatStudentGoToClass(List<Days> dayListThatStudentGoToClasses) {
        List<DaysList> daysLists = new ArrayList<>();
        dayListThatStudentGoToClasses.forEach(days -> {
            DaysList dayFormatted = days.toDaysList();
            daysLists.add(dayFormatted);
        });
        this.dayListThatStudentGoToClasses = daysLists;
    }
}