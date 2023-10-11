package io.github.ValterGabriell.FrequenciaAlunos.domain.frequency;

import io.github.ValterGabriell.FrequenciaAlunos.domain.days.Days;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class FrequencyServiceTest {

    @Test
    void verifyIfDayAlreadySavedOnFrequency() {
        List<Days> days = new ArrayList<>();

        Days day = addNewDay(days);

        boolean contains = days.contains(day);

        Assertions.assertThat(contains).isTrue();

        Days day2 = addNewDay(days);

        boolean contains2 = days.contains(day2);

        Assertions.assertThat(contains2).isFalse();
    }

    private Days addNewDay(List<Days> days) {
        LocalDate date = LocalDate.now();

        Days day = new Days(date);

        if (!days.contains(day)) {
            days.add(day);
            return day;
        }
        return new Days();
    }

}