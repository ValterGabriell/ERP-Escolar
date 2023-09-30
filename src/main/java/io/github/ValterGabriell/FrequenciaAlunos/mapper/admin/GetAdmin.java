package io.github.ValterGabriell.FrequenciaAlunos.mapper.admin;

import java.util.UUID;

public class GetAdmin{
    private UUID id;
    private String username;
    private String email;

    public GetAdmin(String username, String email, UUID id) {
        this.username = username;
        this.email = email;
        this.id = id;
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

    public UUID getId() {
        return id;
    }



    public void setId(UUID id) {
        this.id = id;
    }
}
