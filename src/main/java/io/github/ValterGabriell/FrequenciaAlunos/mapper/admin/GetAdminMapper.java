package io.github.ValterGabriell.FrequenciaAlunos.mapper.admin;

import io.github.ValterGabriell.FrequenciaAlunos.domain.contacts.Contacts;
import org.springframework.hateoas.Links;

import java.util.List;

public class GetAdminMapper {
    private String cnpj;
    private String skid;
    private String firstName;
    private String secondName;
    private String email;

    private List<Contacts> contacts;

    private String loginId;
    private Links links;

    public void setLinks(Links links) {
        this.links = links;
    }

    public GetAdminMapper(
            String cnpj,
            String skid,
            String firstName,
            String secondName,
            String email, List<Contacts> contacts, String loginId, Links links) {
        this.cnpj = cnpj;
        this.skid = skid;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.contacts = contacts;
        this.loginId = loginId;
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

    public String getEmail() {
        return email;
    }

    public List<Contacts> getContacts() {
        return contacts;
    }

    public String getLoginId() {
        return loginId;
    }

    public Links getLinks() {
        return links;
    }
}
