package io.github.ValterGabriell.FrequenciaAlunos.validation;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Day;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Frequency;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Student;
import io.github.ValterGabriell.FrequenciaAlunos.dto.admin.DtoCreateNewAdmin;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.AdminRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.FrequencyRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.StudentsRepository;

public abstract class Validation {
    public Admin validateIfAdminExistsAndReturnIfExistByCnpj(
            AdminRepository adminRepository,
            String cnpj,
            Integer tenant) {
        return null;
    }

    public boolean verifyIfEmailIsCorrectAndThrowAnErrorIfIsNot(String email) throws RequestExceptions {
        return false;
    }

    public void checkIfCnpjAlreadyExistAndThrowAnErrorIfItIs(AdminRepository adminRepository, String cnpj) throws RequestExceptions {
    }

    public void validatingFieldsToCreateNewAdmin(DtoCreateNewAdmin newAdmin) {
    }

    public boolean validateIfAdminHasAlreadyTenant(AdminRepository adminRepository, String cnpj, int tenant) {
        return false;
    }

    public boolean fieldContainsOnlyLetters(String field) {
        return false;
    }

    public boolean fieldContainsOnlyNumbers(String field) {
        return false;
    }


    public boolean isFieldHasNumberExcatlyOfChars(String field, int charNumber) {
        return false;
    }

    public boolean validateIfIsNotEmpty(String field, String exceptionMessage) throws RequestExceptions {
        return false;
    }

    public void verifyIfDayAlreadySavedOnFrequencyAndThrowAnErroIfItIs(Frequency frequency, Day day) {
    }

    public Student validateIfStudentExistsAndReturnIfExist(
            StudentsRepository studentsRepository,
            String studentId,
            int tenantId) {
        return null;
    }

    public void verifyIfFrequencyExists(FrequencyRepository frequencyRepository, String studentSkId, int tenant) {
    }

    ;

    protected boolean validateMonth(String month) {
        return false;
    }
}
