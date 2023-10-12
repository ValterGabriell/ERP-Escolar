package io.github.ValterGabriell.FrequenciaAlunos.domain.frequency;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.ValterGabriell.FrequenciaAlunos.domain.days.Days;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Frequency {
    @Id
    private String id;

    @Column(nullable = false)
    private Integer tenant;
    @OneToMany(targetEntity = Days.class, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Days> daysList;

    public Frequency() {
    }

    public Frequency(Integer tenant) {
        this.tenant = tenant;
    }

    public Integer getTenant() {
        return tenant;
    }

    public List<Days> getDaysList() {
        return daysList;
    }

    public void setDaysList(List<Days> daysList) {
        this.daysList = daysList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
