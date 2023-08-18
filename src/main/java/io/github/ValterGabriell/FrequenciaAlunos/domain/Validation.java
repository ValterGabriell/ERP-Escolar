package io.github.ValterGabriell.FrequenciaAlunos.domain;

import io.github.ValterGabriell.FrequenciaAlunos.domain.days.Days;
import io.github.ValterGabriell.FrequenciaAlunos.domain.frequency.Frequency;
import io.github.ValterGabriell.FrequenciaAlunos.domain.frequency.FrequencyValidation;
import io.github.ValterGabriell.FrequenciaAlunos.domain.students.Student;
import io.github.ValterGabriell.FrequenciaAlunos.domain.students.StudentValidation;
import io.github.ValterGabriell.FrequenciaAlunos.excpetion.ExceptionsValues;
import io.github.ValterGabriell.FrequenciaAlunos.excpetion.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.StudentsRepository;

import java.util.Optional;

public class Validation implements StudentValidation, FrequencyValidation, FieldValidation {
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
        if (cpf.length() != 11) {
            throw new RequestExceptions(ExceptionsValues.ILLEGAL_CPF_LENGTH);
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
}