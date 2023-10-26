package io.github.ValterGabriell.FrequenciaAlunos.helper;

import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;

public interface FieldValidation{
    boolean fieldContainsOnlyLetters(String field);

    boolean isFieldHasNumberExcatlyOfChars(String field, int charNumber);

    boolean validateIfIsNotEmpty(String field, String exceptionMessage) throws RequestExceptions;
}