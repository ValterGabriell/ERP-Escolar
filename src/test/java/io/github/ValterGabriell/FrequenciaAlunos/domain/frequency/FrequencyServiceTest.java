package io.github.ValterGabriell.FrequenciaAlunos.domain.frequency;

import io.github.ValterGabriell.FrequenciaAlunos.domain.days.Day;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class FrequencyServiceTest {

    @Test
    void verifyIfDayAlreadySavedOnFrequency() {
        List<Day> days = new ArrayList<>();

        Day day = addNewDay(days);

        boolean contains = days.contains(day);

        Assertions.assertThat(contains).isTrue();

        Day day2 = addNewDay(days);

        boolean contains2 = days.contains(day2);

        Assertions.assertThat(contains2).isFalse();
    }

    private Day addNewDay(List<Day> days) {
        LocalDate date = LocalDate.now();
        Day day = new Day(date, 10);

        if (!days.contains(day)) {
            days.add(day);
            return day;
        }
        return new Day();
    }

}