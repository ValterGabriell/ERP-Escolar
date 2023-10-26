package io.github.ValterGabriell.FrequenciaAlunos.validation;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Day;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Frequency;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.ExceptionsValues;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;

public class FrequencyValidation extends Validation {
    @Override
    public void verifyIfDayAlreadySavedOnFrequencyAndThrowAnErroIfItIs(Frequency frequency, Day day) {
        if (frequency.getDaysList().contains(day)) {
            throw new RequestExceptions(ExceptionsValues.STUDENT_ALREADY_VALIDATED);
        }
    }
}
