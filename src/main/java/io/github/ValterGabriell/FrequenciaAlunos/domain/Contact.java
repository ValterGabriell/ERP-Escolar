package io.github.ValterGabriell.FrequenciaAlunos.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity(name = "tbl_contatos")
public class Contact  {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID contactId;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private Integer tenant;


    public Contact(String phone, String email, String userId, Integer tenant) {
        this.phone = phone;
        this.email = email;
        this.userId = userId;
        this.tenant = tenant;
    }

    public Contact() {

    }

    public String getPhone() {
        return phone;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTenant(Integer tenant) {
        this.tenant = tenant;
    }

    public String getEmail() {
        return email;
    }
}
