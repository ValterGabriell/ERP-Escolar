package io.github.ValterGabriell.FrequenciaAlunos.domain.average;

import io.github.ValterGabriell.FrequenciaAlunos.domain.students.Student;
import jakarta.persistence.*;

@Entity(name = "tbl_notas")
public class Avegare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(nullable = false)
    private String skid;

    //avaliação
    @Column(name = "evaluation")
    private int evaluation;

    @Column(name = "tenant", nullable = false)
    private Integer tenant;

}
