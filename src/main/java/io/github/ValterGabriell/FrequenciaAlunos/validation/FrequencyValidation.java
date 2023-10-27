package io.github.ValterGabriell.FrequenciaAlunos.validation;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Day;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Frequency;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.ExceptionsValues;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.FrequencyRepository;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.time.Month;

public class FrequencyValidation extends Validation {
    @Override
    public void verifyIfDayAlreadySavedOnFrequencyAndThrowAnErroIfItIs(Frequency frequency, Day day) {
        if (frequency.getDaysList().contains(day)) {
            throw new RequestExceptions(ExceptionsValues.STUDENT_ALREADY_VALIDATED);
        }
    }

    @Override
    public boolean verifyIfFrequencyExists(FrequencyRepository frequencyRepository, String studentSkId, int tenant) {
        Frequency frequency = frequencyRepository.findBySkidAndTenant(studentSkId, tenant);
        return frequency != null;
    }

    @Override
    public boolean validateMonth(String month) {
        Month[] values = Month.values();
        boolean isValid = false;
        for (Month currentValuealue : values) {
            if (currentValuealue.name().equals(month)) {
                isValid = true;
                break;
            }
        }
        return isValid;
    }
}
