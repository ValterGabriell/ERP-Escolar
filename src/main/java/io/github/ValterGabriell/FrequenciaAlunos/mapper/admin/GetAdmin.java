package io.github.ValterGabriell.FrequenciaAlunos.mapper.admin;

import io.github.ValterGabriell.FrequenciaAlunos.domain.HateoasModel;
import io.github.ValterGabriell.FrequenciaAlunos.domain.admins.Admin;
import org.springframework.hateoas.Links;

import java.util.List;

public class GetAdmin {
    private String id;
    private String username;
    private String email;
    private String cnpj;
    private Links links;

    public GetAdmin(String id, String username, String email, String cnpj, Links links) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.cnpj = cnpj;
        this.links = links;
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

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public Admin toAdmin() {
        return new Admin(this.id, this.username, "", this.email, this.cnpj);
    }
}
