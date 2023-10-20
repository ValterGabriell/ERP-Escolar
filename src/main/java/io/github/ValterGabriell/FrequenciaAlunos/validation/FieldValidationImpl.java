package io.github.ValterGabriell.FrequenciaAlunos.validation;

import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.helper.FieldValidation;

public class FieldValidationImpl implements FieldValidation {
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
        boolean isFieldNotNull = !field.isEmpty() || field.isBlank();
        if (!isFieldNotNull) {
            throw new RequestExceptions(exceptionMessage);
        }
        return true;
    }
}
