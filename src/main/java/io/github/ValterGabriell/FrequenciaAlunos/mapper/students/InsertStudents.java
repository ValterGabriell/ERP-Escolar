package io.github.ValterGabriell.FrequenciaAlunos.mapper.students;

import io.github.ValterGabriell.FrequenciaAlunos.domain.students.Student;
import io.github.ValterGabriell.FrequenciaAlunos.validation.ExceptionsValues;
import io.github.ValterGabriell.FrequenciaAlunos.validation.Validation;

import java.time.LocalDateTime;

public class InsertStudents extends Validation {
    private String cpf;

    private String username;
    private String email;

    public InsertStudents(String cpf, String username, String email) {
        this.cpf = cpf;
        this.username = username;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }


    public String getUsername() {
        return username;
    }

    public boolean usernameIsNotNull() {
        return validateIfIsNotEmpty(getUsername(), ExceptionsValues.USERNAME_NULL);
    }

    public boolean emailIsNotNull() {
        return validateIfIsNotEmpty(getEmail(), ExceptionsValues.EMAIL_NULL);
    }

    public boolean cpfIsNull() {
        return validateIfIsNotEmpty(getCpf(), ExceptionsValues.CPF_NULL);
    }

    public boolean usernameHasToBeMoreThanTwoChars() {
        return validateIfIsNotEmpty(getUsername(), ExceptionsValues.USERNAME_ILLEGAL_LENGHT);
    }

    public Student toModel(Integer tenant) {
        return new Student(this.cpf, this.getUsername(), this.getEmail(), LocalDateTime.now(), null, tenant);
    }
}