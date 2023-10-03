package io.github.ValterGabriell.FrequenciaAlunos.domain.students;

import io.github.ValterGabriell.FrequenciaAlunos.domain.admins.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.domain.frequency.Frequency;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Student implements Comparable<Student> {

    @Id
    @Column(name = "cpf", nullable = false)
    private String cpf;
    @Column(name = "nome", nullable = false)
    private String username;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "startDate", nullable = false)
    private LocalDateTime startDate;
    @Column(name = "finishedDate", nullable = true)
    private LocalDateTime finishedDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "frequency_id", referencedColumnName = "id")
    private Frequency frequency;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    public Student(String cpf, String username, String email, LocalDateTime startDate, LocalDateTime finishedDate, Frequency frequency) {
        this.cpf = cpf;
        this.username = username;
        this.email = email;
        this.startDate = startDate;
        this.finishedDate = finishedDate;
        this.frequency = frequency;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Admin getAdmin() {
        return admin;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(LocalDateTime finishedDate) {
        this.finishedDate = finishedDate;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    @Override
    public int compareTo(Student o) {
        return this.username.compareTo(o.getUsername());
    }
}
