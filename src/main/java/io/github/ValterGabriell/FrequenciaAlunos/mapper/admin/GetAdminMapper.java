package io.github.ValterGabriell.FrequenciaAlunos.mapper.admin;

import io.github.ValterGabriell.FrequenciaAlunos.domain.admins.Admin;
import org.springframework.hateoas.Links;

public class GetAdminMapper {
    private String cnpj;
    private String skid;
    private String firstName;
    private String secondName;
    private String email;
    private Links links;

    public GetAdminMapper(String firstName, String email, String cnpj, Links links, String secondName) {
        this.firstName = firstName;
        this.email = email;
        this.cnpj = cnpj;
        this.links = links;
        this.secondName = secondName;
    }


    public GetAdminMapper() {
    }

    public String getSkid() {
        return skid;
    }

    public void setSkid(String skid) {
        this.skid = skid;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public String getSecondName() {
        return secondName;
    }

    public Admin toAdmin() {
        return new Admin(this.firstName, "", this.email, this.cnpj, this.secondName);
    }
}
