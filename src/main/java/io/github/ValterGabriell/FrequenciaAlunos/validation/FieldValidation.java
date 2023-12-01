package io.github.ValterGabriell.FrequenciaAlunos.validation;

import io.github.ValterGabriell.FrequenciaAlunos.exceptions.ExceptionsValues;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;

import java.time.Month;

public class FieldValidation extends Validation {
    @Override
    public boolean fieldContainsOnlyLetters(String field) {
        String regex = "^[a-zA-Z]+$";
        boolean isUsernameLenghtOk = field.matches(regex);
        if (!isUsernameLenghtOk) {
            throw new RequestExceptions(ExceptionsValues.USERNAME_ILLEGAL_CHARS);
        }
        return true;
    }

    @Override
    public boolean isFieldHasNumberExcatlyOfChars(String field, int charNumber) {
        boolean isCpfLenghtOk = field.length() == charNumber;
        if (!isCpfLenghtOk) {
            throw new RequestExceptions(ExceptionsValues.ILLEGAL_CPF_LENGTH);
        }
        return true;
    }

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
