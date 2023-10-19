package io.github.ValterGabriell.FrequenciaAlunos.domain.login;

import jakarta.persistence.*;

import java.util.UUID;

@Entity(name = "tbl_login")
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String login;
    private String password;
    private String token;
    private String refreshToken;

    @Column(name = "tenant", nullable = false)
    private Integer tenant;

    @Column(nullable = false)
    private String skid;



}
