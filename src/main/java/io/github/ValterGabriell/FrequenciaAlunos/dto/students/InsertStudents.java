package io.github.ValterGabriell.FrequenciaAlunos.dto.students;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Student;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.ExceptionsValues;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.validation.FieldValidation;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static io.github.ValterGabriell.FrequenciaAlunos.exceptions.ExceptionsValues.INVALID_BORN_YEAR;

public class InsertStudents extends FieldValidation {
    private String studentId;

    private int bornYear;
    private String firstName;
    private String secondName;
    private String email;

    public InsertStudents(String cpf, String firstName, String email, String secondName, int bornYear) {
        this.studentId = cpf;
        this.firstName = firstName;
        this.email = email;
        this.secondName = secondName;
        this.bornYear = bornYear;
    }

    public int getBornYear() {
        return bornYear;
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
        return new Student(this.studentId, this.getUsername(), this.getEmail(), LocalDateTime.now(), null, tenant,bornYear);
    }

    public boolean checkIfAgeIsMoreThanEighteen(int bornYear) {
        if (bornYear >LocalDate.now().getYear()) throw new RequestExceptions(
                INVALID_BORN_YEAR
        );
        int age = LocalDate.now().getYear() - bornYear;
        return age > 18;
    }
}