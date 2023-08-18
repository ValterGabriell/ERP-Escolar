package io.github.ValterGabriell.FrequenciaAlunos.domain.days.dto;

import java.io.Serializable;

public class DaysList {
    private String date;
    private boolean justified;


    public DaysList(String date, boolean justified) {
        this.date = date;
        this.justified = justified;
    }

    public String getDate() {
        return date;
    }

    public boolean isJustified() {
        return justified;
    }
}
