package io.github.ValterGabriell.FrequenciaAlunos.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class FieldValidationTest {

    @Test
    @DisplayName(value = "it should return true when field has only numbers")
    void fieldContainsOnlyNumbers() {
        FieldValidation fieldValidation = new FieldValidation();
        boolean onlyNumbers = fieldValidation.fieldContainsOnlyNumbers("12345");
        Assertions.assertThat(onlyNumbers).isTrue();
    }

    @Test
    @DisplayName(value = "it should return true when field is not empty")
    void validateIfIsNotEmpty() {
        FieldValidation fieldValidation = new FieldValidation();
        boolean onlyNumbers = fieldValidation.validateIfIsNotEmpty("", "Deve estar completo");
        Assertions.assertThat(onlyNumbers).isTrue();
    }

}