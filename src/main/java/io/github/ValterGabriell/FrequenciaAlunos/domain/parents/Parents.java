package io.github.ValterGabriell.FrequenciaAlunos.domain.parents;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.ValterGabriell.FrequenciaAlunos.domain.contacts.Contacts;
import io.github.ValterGabriell.FrequenciaAlunos.domain.days.Days;
import io.github.ValterGabriell.FrequenciaAlunos.domain.students.Student;
import jakarta.persistence.*;

import java.util.List;

@Entity(name = "tbl_pais")
public class Parents {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String skid;

        @Column(nullable = false)
        private String firstName;

        @Column(nullable = false)
        private String lastName;

        @Column(nullable = false)
        private String identifierNumber;

        @Column(nullable = false)
        private Integer tenant;

        @OneToMany(targetEntity = Student.class, cascade = CascadeType.ALL)
        private List<Student> students;
        @OneToMany(targetEntity = Contacts.class, cascade = CascadeType.ALL)
        private List<Contacts> contacts;
    }

