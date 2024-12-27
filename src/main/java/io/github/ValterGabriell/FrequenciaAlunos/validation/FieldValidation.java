package io.github.ValterGabriell.FrequenciaAlunos.validation;

import io.github.ValterGabriell.FrequenciaAlunos.exceptions.ExceptionsValues;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;

import java.time.Month;

public class FieldValidation extends Validation {
    @Override
    public boolean validateIfIsNotEmpty(String field, String exceptionMessage) throws RequestExceptions {
        boolean isFieldNull = field.isEmpty() || field.isBlank();
        if (isFieldNull) {
            throw new RequestExceptions(exceptionMessage);
        }
        return true;
    }

    @Override
    public boolean fieldContainsOnlyNumbers(String field) {
        if (field.isBlank()) return false;
        String regex = "^[0-9]*$";
        return field.matches(regex);
    }
}
