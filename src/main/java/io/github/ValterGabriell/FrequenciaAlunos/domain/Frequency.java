package io.github.ValterGabriell.FrequenciaAlunos.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Day;
import jakarta.persistence.*;

import java.util.List;

@Entity(name = "tbl_frequencia")
public class Frequency {
    @Id
    private String frequencyId;

    @Column(nullable = false)
    private Integer tenant;
    @OneToMany(targetEntity = Day.class, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Day> dayList;

    @Column(nullable = false)
    private String skid;

    public Frequency() {
    }

    public Frequency(Integer tenant) {
        this.tenant = tenant;
    }

    public Integer getTenant() {
        return tenant;
    }

    public List<Day> getDaysList() {
        return dayList;
    }

    public void setDaysList(List<Day> dayList) {
        this.dayList = dayList;
    }

    public String getFrequencyId() {
        return frequencyId;
    }

    public void setFrequencyId(String id) {
        this.frequencyId = id;
    }

    public String getSkid() {
        return skid;
    }

    public void setSkid(String skid) {
        this.skid = skid;
    }
}
