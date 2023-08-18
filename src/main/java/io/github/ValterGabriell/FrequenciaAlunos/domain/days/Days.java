package io.github.ValterGabriell.FrequenciaAlunos.domain.days;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.ValterGabriell.FrequenciaAlunos.domain.days.dto.DaysList;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

@Entity
public class Days {

    @Id
    private String id;
    private LocalDate date;
    private boolean justified;

    public Days() {
    }

    public Days(LocalDate date) {
        this.date = date;
        this.id = String.valueOf(System.currentTimeMillis());
    }

    public Days(LocalDate date, boolean justified) {
        this.date = date;
        this.justified = false;
    }

    public boolean isJustified() {
        return justified;
    }

    public void setJustified(boolean justified) {
        this.justified = justified;
    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return date != null && date.equals(((Days) o).date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }

    public DaysList toDaysList() {
        return new DaysList(this.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)), this.justified);
    }
}
