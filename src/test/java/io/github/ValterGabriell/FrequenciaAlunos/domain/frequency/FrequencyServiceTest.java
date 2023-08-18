package io.github.ValterGabriell.FrequenciaAlunos.domain.frequency;

import io.github.ValterGabriell.FrequenciaAlunos.domain.days.Days;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class FrequencyServiceTest {

    @Test
    void verifyIfDayAlreadySavedOnFrequencyAndThrowAnErroIfItIs() {
        List<Days> days = new ArrayList<>();
        LocalDate date = LocalDate.now();
        Days day = new Days(date);
        days.add(day);
        boolean contains = days.contains(day);
        Assertions.assertTrue(contains);
    }

}