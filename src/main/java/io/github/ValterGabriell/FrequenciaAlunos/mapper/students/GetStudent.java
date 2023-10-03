package io.github.ValterGabriell.FrequenciaAlunos.mapper.students;

import java.time.LocalDateTime;

public class GetStudent {
    private String cpf;
    private String username;
    private String email;
    private LocalDateTime startDate;
    private String adminId;

    public GetStudent(String cpf, String username, String email, LocalDateTime startDate, String adminId) {
        this.cpf = cpf;
        this.username = username;
        this.email = email;
        this.startDate = startDate;
        this.adminId = adminId;
    }

    public String getCpf() {
        return cpf;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public String getAdminId() {
        return adminId;
    }
}
