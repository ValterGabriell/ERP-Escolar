package io.github.ValterGabriell.FrequenciaAlunos.domain.school_class;

import io.github.ValterGabriell.FrequenciaAlunos.domain.students.Student;
import jakarta.persistence.*;

import java.util.List;

@Entity(name = "tbl_turmas")
public class SchoolClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String skid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "second_name", nullable = false)
    private String secondName;

    @Column(name = "period", nullable = false)
    private String period;

    @Column(name = "year", nullable = false)
    private int year;

    @Column(name = "tenant", nullable = false)
    private Integer tenant;
    @OneToMany(targetEntity = Student.class, cascade = CascadeType.ALL)
    private List<Student> students;
}
