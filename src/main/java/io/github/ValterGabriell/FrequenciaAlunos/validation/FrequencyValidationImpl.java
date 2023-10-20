package io.github.ValterGabriell.FrequenciaAlunos.validation;

import io.github.ValterGabriell.FrequenciaAlunos.domain.days.Days;
import io.github.ValterGabriell.FrequenciaAlunos.domain.frequency.Frequency;
import io.github.ValterGabriell.FrequenciaAlunos.domain.frequency.FrequencyValidation;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;

public class FrequencyValidationImpl implements FrequencyValidation {
    @Override
    public void verifyIfDayAlreadySavedOnFrequencyAndThrowAnErroIfItIs(Frequency frequency, Days day) {
        if (frequency.getDaysList().contains(day)) {
            throw new RequestExceptions(ExceptionsValues.STUDENT_ALREADY_VALIDATED);
        }
    }
}
