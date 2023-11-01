package io.github.ValterGabriell.FrequenciaAlunos.mapper.frequency;

import jakarta.validation.constraints.NotEmpty;

public class JustifyAbscenceDesc {

    @NotEmpty
    private String description;

    public JustifyAbscenceDesc(String description) {
        this.description = description;
    }

    public JustifyAbscenceDesc() {
    }

    public String getDescription() {
        return description;
    }
}
