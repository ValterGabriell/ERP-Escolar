package io.github.ValterGabriell.FrequenciaAlunos.mapper.admin;

import io.github.ValterGabriell.FrequenciaAlunos.domain.admins.Admin;
import org.springframework.hateoas.Links;

public class GetAdminMapper {
    private String skid;
    private String username;
    private String email;
    private String cnpj;
    private Links links;

    public GetAdminMapper(String id, String username, String email, String cnpj, Links links) {
        this.skid = id;
        this.username = username;
        this.email = email;
        this.cnpj = cnpj;
        this.links = links;
    }

    public GetAdminMapper() {
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

    public String getSkid() {
        return skid;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }


    public void setSkid(String skid) {
        this.skid = skid;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public Admin toAdmin() {
        return new Admin(this.skid, this.username, "", this.email, this.cnpj);
    }
}
