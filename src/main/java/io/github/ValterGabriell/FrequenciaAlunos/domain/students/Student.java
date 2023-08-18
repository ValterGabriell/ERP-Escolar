package io.github.ValterGabriell.FrequenciaAlunos.domain.students;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Student implements Comparable<Student> {

    @Id
    @Column(name = "cpf", nullable = false)
    private String cpf;
    @Column(name = "nome", nullable = false)
    private String username;


    public Student(String cpf, String username) {
        this.cpf = cpf;
        this.username = username;
    }

    public Student() {
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int compareTo(Student o) {
        return this.username.compareTo(o.getUsername());
    }
}
