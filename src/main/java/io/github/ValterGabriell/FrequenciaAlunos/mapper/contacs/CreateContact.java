package io.github.ValterGabriell.FrequenciaAlunos.mapper.contacs;

public class CreateContact {
    private String phone;

    private String email;


    public CreateContact(String phone, String email) {
        this.phone = phone;
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
