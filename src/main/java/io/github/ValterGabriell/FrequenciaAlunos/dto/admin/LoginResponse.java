package io.github.ValterGabriell.FrequenciaAlunos.dto.admin;

import io.github.ValterGabriell.FrequenciaAlunos.domain.ModulesEntity;
import io.github.ValterGabriell.FrequenciaAlunos.helper.MODULE;

import java.util.List;

public class LoginResponse {
    private String apiKey;
    private Integer tenant;
    private List<MODULE> modules;

    public LoginResponse(String apiKey, List<MODULE> modules, Integer tenant) {
        this.apiKey = apiKey;
        this.modules = modules;
        this.tenant = tenant;
    }

    public LoginResponse() {
    }

    public String getApiKey() {
        return apiKey;
    }

    public List<MODULE> getModules() {
        return modules;
    }

    public Integer getTenant() {
        return tenant;
    }
}
