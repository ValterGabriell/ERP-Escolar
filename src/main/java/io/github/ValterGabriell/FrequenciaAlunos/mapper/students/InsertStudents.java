package io.github.ValterGabriell.FrequenciaAlunos.mapper.students;

import io.github.ValterGabriell.FrequenciaAlunos.domain.students.Student;
import io.github.ValterGabriell.FrequenciaAlunos.validation.ExceptionsValues;
import io.github.ValterGabriell.FrequenciaAlunos.validation.FieldValidationImpl;

import java.time.LocalDateTime;

public class InsertStudents extends FieldValidationImpl {
    private String studentId;

    private String firstName;
    private String secondName;
    private String email;

    public InsertStudents(String cpf, String firstName, String email, String secondName) {
        this.studentId = cpf;
        this.firstName = firstName;
        this.email = email;
        this.secondName = secondName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public String getStudentId() {
        return studentId;
    }


    public String getUsername() {
        return firstName;
    }

    public boolean usernameIsNotNull() {
        return validateIfIsNotEmpty(getUsername(), ExceptionsValues.USERNAME_NULL);
    }

    public boolean emailIsNotNull() {
        return validateIfIsNotEmpty(getEmail(), ExceptionsValues.EMAIL_NULL);
    }

    public boolean cpfIsNull() {
        return validateIfIsNotEmpty(getStudentId(), ExceptionsValues.CPF_NULL);
    }

    public boolean usernameHasToBeMoreThanTwoChars() {
        return validateIfIsNotEmpty(getUsername(), ExceptionsValues.USERNAME_ILLEGAL_LENGHT);
    }

    public Student toModel(Integer tenant) {
        return new Student(this.studentId, this.getUsername(), this.getEmail(), LocalDateTime.now(), null, tenant);
    }
}