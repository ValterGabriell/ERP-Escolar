package io.github.ValterGabriell.FrequenciaAlunos.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class FrequencyValidationTest {

    @Test
    void validateMonth() {
        String month = "JANUARY";
        FrequencyValidation frequencyValidation = new FrequencyValidation();
        boolean validated = frequencyValidation.validateMonth(month);
        Assertions.assertThat(validated).isTrue();
    }
}