package io.github.ValterGabriell.FrequenciaAlunos.domain.days;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.days.DaysList;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

@Entity(name = "tbl_dias")
public class Day {

    @Id
    private String dayId;

    @Column(nullable = false)
    private String skid;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private boolean justified;
    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private Integer tenant;

    public Day() {
    }

    public Day(LocalDate date, Integer tenant) {
        this.date = date;
        this.dayId = String.valueOf(System.currentTimeMillis());
        this.tenant = tenant;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isJustified() {
        return justified;
    }

    public void setJustified(boolean justified) {
        this.justified = justified;
    }

    @JsonIgnore
    public String getDayId() {
        return dayId;
    }

    public void setDayId(String id) {
        this.dayId = id;
    }

    public LocalDate getDate() {
        return date;
    }



    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getSkid() {
        return skid;
    }

    public void setSkid(String skid) {
        this.skid = skid;
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
        return date != null && date.equals(((Day) o).date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }

    public DaysList toDaysList() {
        return new DaysList(this.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)), this.justified);
    }
}
