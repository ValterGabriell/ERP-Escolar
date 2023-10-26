package io.github.ValterGabriell.FrequenciaAlunos.validation;

public interface DocumentsValidation {
    void checkIfStudentCpfAreCorrectAndThrowExceptionIfItIs(String cpf);
    void checkIfAdminCnpjIsCorrect(String cnpj);
}
