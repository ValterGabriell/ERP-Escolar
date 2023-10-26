package io.github.ValterGabriell.FrequenciaAlunos.validation;

import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;

public class DocumentsValidationImpl implements DocumentsValidation {


    @Override
    public void checkIfStudentCpfAreCorrectAndThrowExceptionIfItIs(String cpf) {
        String regex = "^[0-9]*$";
        boolean matches = cpf.matches(regex);
        if (matches) {
            if (cpf.length() != 11) {
                throw new RequestExceptions(ExceptionsValues.ILLEGAL_CPF_LENGTH);
            }
        } else {
            throw new RequestExceptions(ExceptionsValues.DONT_CONTAINS_ONLY_NUMBERS);
        }
    }

    @Override
    public void checkIfAdminCnpjIsCorrect(String cnpj) {
        FieldValidationImpl fieldValidation = new FieldValidationImpl();
        String regex = "^[0-9]*$";
        boolean matches = cnpj.matches(regex);
        if (matches) {
            boolean fieldHasNumberExcatlyOfChars = fieldValidation.isFieldHasNumberExcatlyOfChars(cnpj, 14);
            if (!fieldHasNumberExcatlyOfChars) {
                throw new RequestExceptions(ExceptionsValues.ILLEGAL_CNPJ_LENGTH);
            }
        } else {
            throw new RequestExceptions(ExceptionsValues.DONT_CONTAINS_ONLY_NUMBERS);
        }
    }
}
