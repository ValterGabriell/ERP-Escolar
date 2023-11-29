package io.github.ValterGabriell.FrequenciaAlunos.dto.admin;

public class LoginDTO {
    private String cnpj;
    private String password;

    public LoginDTO(String cnpj, String password) {
        this.cnpj = cnpj;
        this.password = password;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getPassword() {
        return password;
    }
}
