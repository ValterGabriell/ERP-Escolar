package io.github.ValterGabriell.FrequenciaAlunos.dto.frequency;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Day;
import io.github.ValterGabriell.FrequenciaAlunos.dto.days.DaysList;

import java.util.ArrayList;
import java.util.List;

public class ResponseDaysThatStudentGoToClass {
    private String studentSkId;

    private List<DaysList> dayListThatStudentGoToClasses;

    public ResponseDaysThatStudentGoToClass() {
    }

    public String getStudentSkId() {
        return studentSkId;
    }


    public void setStudentSkId(String studentSkId) {
        this.studentSkId = studentSkId;
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