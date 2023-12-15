package io.github.ValterGabriell.FrequenciaAlunos.domain;

import io.github.ValterGabriell.FrequenciaAlunos.helper.MODULE;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.List;
import java.util.UUID;

@Entity(name = "tbl_entity")
public class ModulesEntity {
    @Id
    private String id;

    private String tenant;
    private List<MODULE> modules;

    public ModulesEntity(String tenant, List<MODULE> modules) {
        this.id = UUID.randomUUID().toString();
        this.tenant = tenant;
        this.modules = modules;
    }

    public ModulesEntity() {

    }

    public void setModules(List<MODULE> modules) {
        this.modules = modules;
    }

    public String getTenant() {
        return tenant;
    }

    public List<MODULE> getModules() {
        return modules;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
