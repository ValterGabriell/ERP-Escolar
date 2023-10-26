package io.github.ValterGabriell.FrequenciaAlunos.mapper.admin;

import io.github.ValterGabriell.FrequenciaAlunos.domain.contacts.Contact;
import org.springframework.hateoas.Links;

import java.util.List;

public class GetAdminMapper {
    private String cnpj;
    private String skid;
    private String firstName;
    private String secondName;
    private String email;

    private List<Contact> contacts;
    private Links links;

    public void setLinks(Links links) {
        this.links = links;
    }

    public GetAdminMapper(
            String cnpj,
            String skid,
            String firstName,
            String secondName,
            String email, List<Contact> contacts, Links links) {
        this.cnpj = cnpj;
        this.skid = skid;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.contacts = contacts;
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

    public List<Contact> getContacts() {
        return contacts;
    }


    public Links getLinks() {
        return links;
    }
}
