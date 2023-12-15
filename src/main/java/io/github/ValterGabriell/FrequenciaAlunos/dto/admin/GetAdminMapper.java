package io.github.ValterGabriell.FrequenciaAlunos.dto.admin;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Contact;
import org.springframework.hateoas.Links;

import java.util.List;

public class GetAdminMapper {
    private String cnpj;
    private String skid;
    private String firstName;
    private String secondName;
    private Links links;

    public void setLinks(Links links) {
        this.links = links;
    }

    public GetAdminMapper(
            String cnpj,
            String skid,
            String firstName,
            String secondName,
          Links links) {
        this.cnpj = cnpj;
        this.skid = skid;
        this.firstName = firstName;
        this.secondName = secondName;
        this.links = links;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getSkid() {
        return skid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }



    public Links getLinks() {
        return links;
    }
}
