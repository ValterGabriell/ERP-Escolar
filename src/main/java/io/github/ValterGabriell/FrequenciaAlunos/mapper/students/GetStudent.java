package io.github.ValterGabriell.FrequenciaAlunos.mapper.students;

import org.springframework.hateoas.Links;

import java.time.LocalDateTime;

public class GetStudent implements Comparable<GetStudent>  {
    private String studentIdentifier;
    private String username;
    private String email;
    private String skid;
    private LocalDateTime startDate;
    private String adminId;
    private String schoolClass;

    private Links links;

    public GetStudent(String studentIdentifier, String username, String email, LocalDateTime startDate,
                      String adminId, Links links, String skid,String schoolClass) {
        this.studentIdentifier = studentIdentifier;
        this.username = username;
        this.email = email;
        this.startDate = startDate;
        this.adminId = adminId;
        this.links = links;
        this.skid = skid;
        this.schoolClass = schoolClass;
    }

    public String getStudentIdentifier() {
        return studentIdentifier;
    }

    public String getSkid() {
        return skid;
    }

    public void setSkid(String skid) {
        this.skid = skid;
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

    public String getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(String schoolClass) {
        this.schoolClass = schoolClass;
    }

    @Override
    public int compareTo(GetStudent o) {
        return this.username.compareTo(o.getUsername());
    }
}
