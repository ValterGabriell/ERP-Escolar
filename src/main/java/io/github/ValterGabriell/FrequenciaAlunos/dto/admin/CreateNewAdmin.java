package io.github.ValterGabriell.FrequenciaAlunos.dto.admin;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Contact;
import io.github.ValterGabriell.FrequenciaAlunos.dto.contacs.CreateContact;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.function.Function;

public class CreateNewAdmin {

    @NotBlank(message = "O campo first name não pode estar vazio")
    private String firstName;
    @NotBlank(message = "O campo second name não pode estar vazio")
    private String secondName;
    @NotBlank(message = "O campo password não pode estar vazio")
    private String password;
    @NotBlank(message = "O campo CNPJ não pode estar vazio")
    private String cnpj;
    @NotNull(message = "A lista de contatos precisa ser preenchida")
    private List<CreateContact> contacts;

    public CreateNewAdmin(
            String firstName,
            String password,
            String cnpj,
            String secondName,
            List<CreateContact> contacts
    ) {
        this.firstName = firstName;
        this.password = password;
        this.cnpj = cnpj;
        this.secondName = secondName;
        this.contacts = contacts;
    }

    public String getCnpj() {
        return cnpj;
    }

    public List<CreateContact> getContacts() {
        return contacts;
    }

    public Admin toAdmin() {

        Function<CreateContact, Contact>
                createContactContactFunction = (val) -> {
            return new Contact(val.getPhone(), val.getEmail(), "", -1);
        };

        List<Contact> collect = this.contacts.stream().map(createContactContactFunction).toList();
        return new Admin(
                this.firstName,
                this.password,
                this.cnpj,
                this.secondName,
                collect
        );
    }


}
