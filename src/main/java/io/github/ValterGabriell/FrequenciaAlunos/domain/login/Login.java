package io.github.ValterGabriell.FrequenciaAlunos.domain.login;

import io.github.ValterGabriell.FrequenciaAlunos.util.GenerateSKId;
import jakarta.persistence.*;

import java.util.UUID;

@Entity(name = "tbl_login")
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;
    private String token;
    private String refreshToken;

    @Column(nullable = false)
    private Integer tenant;

    @Column(nullable = true)
    private String skid;

    public Login() {
    }

    public String getId() {
        return id;
    }

    public String getSkid() {
        return skid;
    }

    public Login(String login, String password, Integer tenant) {
        this.login = login;
        this.password = password;
        this.tenant = tenant;
    }

    public void setSkid(String skid) {
        this.skid = skid;
    }
}
