package io.github.ValterGabriell.FrequenciaAlunos.dto.schoolclass;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Salas;

import java.util.List;

public class CreateSC {
    private String name;

    public CreateSC(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Salas toSC() {
        return new Salas(
                this.name
        );
    }
}
