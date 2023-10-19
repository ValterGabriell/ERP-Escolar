package io.github.ValterGabriell.FrequenciaAlunos.domain.roles;

import jakarta.persistence.*;

@Entity(name = "tbl_role")
public class UserRole {
    @Id
    private Long userId;

    @Column(nullable = false, unique = true)
    private ROLES roleName;
}
