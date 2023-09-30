package io.github.ValterGabriell.FrequenciaAlunos.domain.frequency;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.ValterGabriell.FrequenciaAlunos.domain.days.Days;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Frequency {
    @Id
    private String id;
    @OneToMany(targetEntity = Days.class, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Days> daysList;

    public void setId(String id) {
        this.id = id;
    }

    public List<Days> getDaysList() {
        return daysList;
    }

    public void setDaysList(List<Days> daysList) {
        this.daysList = daysList;
    }

    public Frequency() {
    }

    public String getId() {
        return id;
    }

}
