package io.github.ValterGabriell.FrequenciaAlunos.domain.admins.dto;

public class GetAdmin{
    private String username;
    private String email;

    public GetAdmin(String username, String email) {
        this.username = username;
        this.email = email;
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


}
