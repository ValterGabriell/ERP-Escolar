package io.github.ValterGabriell.FrequenciaAlunos.mapper.admin;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Roles;
import io.github.ValterGabriell.FrequenciaAlunos.domain.students.Student;

import java.util.List;
import java.util.UUID;

public class GetAdmin{
    private String id;
    private String username;
    private String email;
    private String cnpj;
    private List<Roles> roles;

    public GetAdmin(String id, String username, String email, String cnpj, List<Roles> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.cnpj = cnpj;
        this.roles = roles;
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

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public void setId(String id) {
        this.id = id;
    }
}
