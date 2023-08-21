package io.github.ValterGabriell.FrequenciaAlunos.domain.admins;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Roles;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.List;
import java.util.UUID;

@Entity(name = "table_admin")
public class Admin {
    @Id
    private UUID id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private List<Roles> roles;

    public Admin(UUID id, String username, String password, String email, List<Roles> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public Admin() {
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
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
