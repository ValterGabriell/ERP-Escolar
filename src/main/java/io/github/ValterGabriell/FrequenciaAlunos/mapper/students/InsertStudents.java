package io.github.ValterGabriell.FrequenciaAlunos.mapper.students;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Validation;
import io.github.ValterGabriell.FrequenciaAlunos.domain.students.Student;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.ExceptionsValues;

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

    public boolean usernameIsNull() {
        return validateIfIsEmpty(getUsername(), ExceptionsValues.USERNAME_NULL);
    }

    public boolean emailIsNull() {
        return validateIfIsEmpty(getEmail(), ExceptionsValues.EMAIL_NULL);
    }

    public boolean cpfIsNull() {
        return validateIfIsEmpty(getCpf(), ExceptionsValues.CPF_NULL);
    }

    public boolean usernameHasToBeMoreThanTwoChars() {
        return validateIfIsEmpty(getUsername(), ExceptionsValues.USERNAME_ILLEGAL_LENGHT);
    }

    public Student toModel() {
        return new Student(this.cpf, this.username, this.email, LocalDateTime.now(), null, null);
    }
}