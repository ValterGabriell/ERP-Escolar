package io.github.ValterGabriell.FrequenciaAlunos.validation;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Student;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.ExceptionsValues;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.StudentsRepository;

import java.util.Optional;

public class StudentValidation extends Validation {
    @Override
    public Student validateIfStudentExistsAndReturnIfExist(StudentsRepository studentsRepository, String studentSkId, int tenantId) {
        Optional<Student> student = studentsRepository.findBySkidAndTenant(studentSkId, tenantId);
        if (student.isEmpty()) {
            throw new RequestExceptions(ExceptionsValues.USER_NOT_FOUND + " " + studentSkId);
        }
        return student.get();
    }
}
