package io.github.ValterGabriell.FrequenciaAlunos.dto.admin;

import io.github.ValterGabriell.FrequenciaAlunos.domain.ModulesEntity;

import java.util.List;

public class LoginResponse {
    private String apiKey;
    private ModulesEntity modules;

    public LoginResponse(String apiKey, ModulesEntity modules) {
        this.apiKey = apiKey;
        this.modules = modules;
    }

    public LoginResponse() {
    }

    public String getApiKey() {
        return apiKey;
    }

    public ModulesEntity getModules() {
        return modules;
    }
}
