package io.github.ValterGabriell.FrequenciaAlunos.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "tbl_api_key")
public class ApiKeyEntity {
    @Id
    private String id;
    private String apiKey;
    private Integer tenant;
    private LocalDate expireDate;

    public ApiKeyEntity(String apiKey, Integer tenant, LocalDate expireDate) {
        this.id = UUID.randomUUID().toString();
        this.apiKey = apiKey;
        this.tenant = tenant;
        this.expireDate = expireDate.plusDays(8);
    }

    public ApiKeyEntity() {
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setTenant(Integer client) {
        this.tenant = client;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public Integer getTenant() {
        return tenant;
    }

    public String getApiKey() {
        return apiKey;
    }
}
