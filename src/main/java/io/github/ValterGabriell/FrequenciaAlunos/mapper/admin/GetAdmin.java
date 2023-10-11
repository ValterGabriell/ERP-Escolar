package io.github.ValterGabriell.FrequenciaAlunos.mapper.admin;

import java.util.List;

public class GetAdmin{
    private String id;
    private String username;
    private String email;
    private String cnpj;

    public GetAdmin(String id, String username, String email, String cnpj) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.cnpj = cnpj;
    }

    public GetAdmin() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }


    public void setId(String id) {
        this.id = id;
    }
}
