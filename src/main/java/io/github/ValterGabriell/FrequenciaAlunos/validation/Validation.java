package io.github.ValterGabriell.FrequenciaAlunos.validation;

import io.github.ValterGabriell.FrequenciaAlunos.domain.FieldValidation;
import io.github.ValterGabriell.FrequenciaAlunos.domain.admins.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.domain.admins.AdminValidation;
import io.github.ValterGabriell.FrequenciaAlunos.domain.days.Days;
import io.github.ValterGabriell.FrequenciaAlunos.domain.frequency.Frequency;
import io.github.ValterGabriell.FrequenciaAlunos.domain.frequency.FrequencyValidation;
import io.github.ValterGabriell.FrequenciaAlunos.domain.students.Student;
import io.github.ValterGabriell.FrequenciaAlunos.domain.students.StudentValidation;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.AdminRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.StudentsRepository;

import java.util.Optional;

public class Validation implements StudentValidation, FrequencyValidation, FieldValidation, AdminValidation {
    @Override
    public Student validateIfStudentExistsAndReturnIfExist(StudentsRepository studentsRepository, String studentId) {
        Optional<Student> student = studentsRepository.findById(studentId);
        if (student.isEmpty()) {
            throw new RequestExceptions(ExceptionsValues.USER_NOT_FOUND + " " + studentId);
        }
        return student.get();
    }

    @Override
    public void checkIfStudentCpfAreCorrect(String cpf) {
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
    public boolean validateIfIsEmpty(String field, String exceptionMessage) throws RequestExceptions {
        boolean isFieldNotNull = !field.isEmpty() || field.isBlank();
        if (!isFieldNotNull) {
            throw new RequestExceptions(exceptionMessage);
        }
        return true;
    }

    @Override
    public void verifyIfDayAlreadySavedOnFrequencyAndThrowAnErroIfItIs(Frequency frequency, Days day) {
        if (frequency.getDaysList().contains(day)) {
            throw new RequestExceptions(ExceptionsValues.STUDENT_ALREADY_VALIDATED);
        }
    }

    @Override
    public boolean validateIfAdminExistsAndReturnIfExist_ByCnpj(AdminRepository adminRepository, String cnpj) {
        Optional<Admin> admin = adminRepository.findByCnpj(cnpj);
        return admin.isPresent();
    }

    @Override
    public Admin validateIfAdminExistsAndReturnIfExist_BySkId(AdminRepository adminRepository, String skId) {
        Optional<Admin> admin = adminRepository.findBySkid(skId);
        return admin.orElse(null);
    }

    @Override
    public void checkIfAdminCnpjIsCorrect(String cnpj) {
        String regex = "^[0-9]*$";
        boolean matches = cnpj.matches(regex);
        if (matches) {
            boolean fieldHasNumberExcatlyOfChars = isFieldHasNumberExcatlyOfChars(cnpj, 14);
            if (!fieldHasNumberExcatlyOfChars) {
                throw new RequestExceptions(ExceptionsValues.ILLEGAL_CNPJ_LENGTH);
            }
        } else {
            throw new RequestExceptions(ExceptionsValues.DONT_CONTAINS_ONLY_NUMBERS);
        }
    }
}