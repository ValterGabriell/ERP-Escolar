package io.github.ValterGabriell.FrequenciaAlunos.domain.students;

import io.github.ValterGabriell.FrequenciaAlunos.mapper.students.InsertStudents;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudentsServiceTest {

    private InsertStudents studentUsernameTest;
    private InsertStudents studentCpfTest;

    @BeforeEach
    public void setUp() {
        studentUsernameTest = new InsertStudents("0", "ana", "","ana@gmail.com");
        studentCpfTest = new InsertStudents("00000000000", "","", "email@.com");
    }

    @Test
    @DisplayName("A username should be not null and return true when it is")
    void isUsernameNotNull_ReturnTrue_WhenUsernameIsNotNull() {
        Assertions.assertTrue(studentUsernameTest.usernameIsNotNull());
    }

    @Test
    @DisplayName("A cpf should be not null and return true when it is")
    void isCpfNotNull_ReturnTrue_WhenUsernameIsNotNull() {
        Assertions.assertTrue(studentCpfTest.cpfIsNull());
    }

    @Test
    @DisplayName("A username should have only letters and no numbers")
    void isUsernameOnlyLetters() {
        Assertions.assertTrue(studentUsernameTest.fieldContainsOnlyLetters("Username"));
    }

    @Test
    @DisplayName("A username should have more than 2 characters and return true when it is")
    void isUsernameBiggerThan2Chars() {
        Assertions.assertTrue(studentUsernameTest.usernameHasToBeMoreThanTwoChars());
    }

    @Test
    @DisplayName("cpf should have exactly 11 characters and return true when it is")
    void cpfLenght() {
        Assertions.assertTrue(studentCpfTest.isFieldHasNumberExcatlyOfChars(studentCpfTest.getCpf(), 11));
    }
}