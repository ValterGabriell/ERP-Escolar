package io.github.ValterGabriell.FrequenciaAlunos.domain.school_class;

import io.github.ValterGabriell.FrequenciaAlunos.domain.discipline.Discipline;
import io.github.ValterGabriell.FrequenciaAlunos.domain.professors.Professor;
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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String secondName;

    @Column(nullable = false)
    private String period;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private Integer tenant;
    @OneToMany(targetEntity = Student.class, cascade = CascadeType.ALL)
    private List<Student> students;

    @OneToMany(targetEntity = Discipline.class, cascade = CascadeType.ALL)
    private List<Discipline> disciplines;

    @ManyToMany(targetEntity = Professor.class)
    private List<Professor> professors;
}
