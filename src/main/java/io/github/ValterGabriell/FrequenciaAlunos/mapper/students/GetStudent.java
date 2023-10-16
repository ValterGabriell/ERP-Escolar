package io.github.ValterGabriell.FrequenciaAlunos.mapper.students;

import io.github.ValterGabriell.FrequenciaAlunos.domain.students.Student;
import org.springframework.hateoas.Links;

import java.time.LocalDateTime;

public class GetStudent implements Comparable<GetStudent>  {
    private String cpf;
    private String username;
    private String email;
    private LocalDateTime startDate;
    private String adminId;

    private Links links;

    public GetStudent(String cpf, String username, String email, LocalDateTime startDate, String adminId, Links links) {
        this.cpf = cpf;
        this.username = username;
        this.email = email;
        this.startDate = startDate;
        this.adminId = adminId;
        this.links = links;
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

    public Links getLinks() {
        return links;
    }

    @Override
    public int compareTo(GetStudent o) {
        return this.username.compareTo(o.getUsername());
    }
}
