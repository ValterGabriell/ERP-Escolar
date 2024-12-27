package io.github.ValterGabriell.FrequenciaAlunos.dto.admin;

import io.github.ValterGabriell.FrequenciaAlunos.helper.MODULE;

import java.util.List;

public class LoginResponse {
    private Integer tenant;

    public LoginResponse(Integer tenant) {
        this.tenant = tenant;
    }

    public LoginResponse() {
    }

    public Integer getTenant() {
        return tenant;
    }
}
