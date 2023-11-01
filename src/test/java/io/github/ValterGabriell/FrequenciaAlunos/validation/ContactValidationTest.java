package io.github.ValterGabriell.FrequenciaAlunos.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContactValidationTest {

    @Test
    @DisplayName(value = "it should pass when email is correct")
    void verifyIfEmailIsCorrect() {
        ContactValidation contactValidation = new ContactValidation();
        String email = "emailteste@gmail.com";
        boolean ifEmailIsCorrect = contactValidation.verifyIfEmailIsCorrectAndThrowAnErrorIfIsNot(email);
        Assertions.assertThat(ifEmailIsCorrect).isTrue();
    }
}