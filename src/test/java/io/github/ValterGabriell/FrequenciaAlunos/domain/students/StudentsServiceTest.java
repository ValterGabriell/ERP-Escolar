package io.github.ValterGabriell.FrequenciaAlunos.domain.students;

import io.github.ValterGabriell.FrequenciaAlunos.dto.students.InsertStudents;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.github.ValterGabriell.FrequenciaAlunos.exceptions.ExceptionsValues.INVALID_BORN_YEAR;

class StudentsServiceTest {

    private InsertStudents insertStudents;
    @BeforeEach
    public void setUp() {
        insertStudents = new InsertStudents("11111111111", "ana", "", "ana@gmail.com", 2023);
    }

    @Test
    @DisplayName("A username should be not null and return true when it is")
    void isUsernameNotNull_ReturnTrue_WhenUsernameIsNotNull() {
        Assertions.assertTrue(insertStudents.usernameIsNotNull());
    }

    @Test
    @DisplayName("A cpf should be not null and return true when it is")
    void isCpfNotNull_ReturnTrue_WhenUsernameIsNotNull() {
        Assertions.assertTrue(insertStudents.cpfIsNull());
    }

    @Test
    @DisplayName("A username should have only letters and no numbers")
    void isUsernameOnlyLetters() {
        Assertions.assertTrue(insertStudents.fieldContainsOnlyLetters("Username"));
    }

    @Test
    @DisplayName("A username should have more than 2 characters and return true when it is")
    void isUsernameBiggerThan2Chars() {
        Assertions.assertTrue(insertStudents.usernameHasToBeMoreThanTwoChars());
    }

    @Test
    @DisplayName("cpf should have exactly 11 characters and return true when it is")
    void cpfLenght() {
        Assertions.assertTrue(insertStudents.isFieldHasNumberExcatlyOfChars(insertStudents.getStudentId(), 11));
    }

    @Test
    @DisplayName("student age should be more than 18")
    void setStudentAgeTest() {
        int bornYear = 2000;
        boolean isMoreThanEighteen = insertStudents.checkIfAgeIsMoreThanEighteen(bornYear);
        org.assertj.core.api.Assertions.assertThat(isMoreThanEighteen).isTrue();
    }
}