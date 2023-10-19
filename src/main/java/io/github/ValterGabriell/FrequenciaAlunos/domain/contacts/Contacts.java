package io.github.ValterGabriell.FrequenciaAlunos.domain.contacts;

import jakarta.persistence.*;

@Entity(name = "tbl_contacts")
public class Contacts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String userId;

    @Column(name = "tenant", nullable = false)
    private Integer tenant;

}
