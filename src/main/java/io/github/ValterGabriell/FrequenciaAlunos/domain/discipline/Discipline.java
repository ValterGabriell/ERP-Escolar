package io.github.ValterGabriell.FrequenciaAlunos.domain.discipline;

import jakarta.persistence.*;

@Entity(name = "tbl_disciplina")
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String skid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int tenant;

    @Column(nullable = false)
    private String professorId;
}
