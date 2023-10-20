package io.github.ValterGabriell.FrequenciaAlunos.domain.professors;


import io.github.ValterGabriell.FrequenciaAlunos.domain.contacts.Contacts;
import io.github.ValterGabriell.FrequenciaAlunos.domain.school_class.SchoolClass;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "tbl_professores")
public class Professors {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String skid;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Double average;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private int tenant;

    @Column(name = "startDate", nullable = false)
    private LocalDateTime startDate;
    @Column(name = "finishedDate", nullable = true)
    private LocalDateTime finishedDate;

    @OneToMany(targetEntity = Contacts.class, cascade = CascadeType.ALL)
    private List<Contacts> contacts;

    @ManyToMany(targetEntity = SchoolClass.class)
    private List<SchoolClass> schoolClasses;
}
