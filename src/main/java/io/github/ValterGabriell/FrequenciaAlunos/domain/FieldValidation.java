package io.github.ValterGabriell.FrequenciaAlunos.domain;

import io.github.ValterGabriell.FrequenciaAlunos.excpetion.RequestExceptions;

public interface FieldValidation{
    boolean fieldContainsOnlyLetters(String field);

    boolean isFieldHasNumberExcatlyOfChars(String field, int charNumber);

    boolean validateIfIsEmpty(String field, String exceptionMessage) throws RequestExceptions;
}