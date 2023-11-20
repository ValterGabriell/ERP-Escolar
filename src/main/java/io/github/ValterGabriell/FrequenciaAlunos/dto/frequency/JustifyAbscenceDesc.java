package io.github.ValterGabriell.FrequenciaAlunos.dto.frequency;

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
