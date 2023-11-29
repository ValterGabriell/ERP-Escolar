package io.github.ValterGabriell.FrequenciaAlunos.dto.admin;

import io.github.ValterGabriell.FrequenciaAlunos.helper.MODULE;

import java.util.List;

import java.util.List;

public class ModulesDTO {

    private List<MODULE> modules;

    public ModulesDTO() {
    }

    public ModulesDTO(List<MODULE> modules) {
        this.modules = modules;
    }

    public List<MODULE> getModules() {
        return modules;
    }

    public void setModules(List<MODULE> modules) {
        this.modules = modules;
    }
}

