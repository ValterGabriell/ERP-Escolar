package io.github.ValterGabriell.FrequenciaAlunos.domain.roles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "tbl_role")
public class UserRole {
    @Id
    private Long userId;

    @Column(nullable = false, unique = true)
    private ROLES roleName;
}
