package io.github.ValterGabriell.FrequenciaAlunos.dto.schoolclass;

import io.github.ValterGabriell.FrequenciaAlunos.domain.SchoolClass;

import java.util.List;

public class CreateSC {
    private String name;
    private String secondName;
    private String period;
    private int year;

    private List<String> professorsSkId;

    public CreateSC(String name, String secondName, String period, int year, List<String> professorsSkId) {
        this.name = name;
        this.secondName = secondName;
        this.period = period;
        this.year = year;
        this.professorsSkId = professorsSkId;
    }

    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getPeriod() {
        return period;
    }

    public int getYear() {
        return year;
    }

    public List<String> getProfessors() {
        return professorsSkId;
    }

    public void setProfessorsSkId(List<String> professorsSkId) {
        this.professorsSkId = professorsSkId;
    }

    public SchoolClass toSC() {
        return new SchoolClass(
                this.name,
                this.secondName,
                this.period,
                this.year
        );
    }
}
