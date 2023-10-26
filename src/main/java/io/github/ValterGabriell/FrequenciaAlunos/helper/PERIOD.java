package io.github.ValterGabriell.FrequenciaAlunos.helper;

public enum PERIOD {

    MANHA("manhã"),
    TARDE("tarde"),
    NOITE("noite");

    private String name;

    PERIOD(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
