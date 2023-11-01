package io.github.ValterGabriell.FrequenciaAlunos.mapper.days;

import java.io.Serializable;

public class DaysList {
    private String date;
    private String description;
    private boolean justified;


    public DaysList(String date, String description, boolean justified) {
        this.date = date;
        this.justified = justified;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public boolean isJustified() {
        return justified;
    }
}
