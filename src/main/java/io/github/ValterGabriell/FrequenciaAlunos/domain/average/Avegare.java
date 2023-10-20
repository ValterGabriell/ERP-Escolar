package io.github.ValterGabriell.FrequenciaAlunos.domain.average;

import io.github.ValterGabriell.FrequenciaAlunos.domain.students.Student;
import jakarta.persistence.*;

@Entity(name = "tbl_notas_alunos")
public class Avegare {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private String studentId;

    @Column(nullable = false)
    private String skid;

    //avaliação
    @Column(nullable = false)
    private int evaluation;

    @Column(nullable = false)
    private Integer tenant;

}
