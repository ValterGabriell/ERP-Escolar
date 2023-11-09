package io.github.ValterGabriell.FrequenciaAlunos.dto.contacs;

import jakarta.validation.constraints.NotBlank;

public class CreateContact {
    @NotBlank(message = "O telefone precisa ser fornecido")
    private String phone;

    @NotBlank(message = "O email precisa ser fornecido")
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
