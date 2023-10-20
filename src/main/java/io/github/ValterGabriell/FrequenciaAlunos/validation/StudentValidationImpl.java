package io.github.ValterGabriell.FrequenciaAlunos.validation;

import io.github.ValterGabriell.FrequenciaAlunos.domain.students.Student;
import io.github.ValterGabriell.FrequenciaAlunos.domain.students.StudentValidation;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.StudentsRepository;

import java.util.Optional;

public class StudentValidationImpl implements StudentValidation {
    @Override
    public Student validateIfStudentExistsAndReturnIfExist(StudentsRepository studentsRepository, String studentId, int tenantId) {
        Optional<Student> student = studentsRepository.findById(studentId, tenantId);
        if (student.isEmpty()) {
            throw new RequestExceptions(ExceptionsValues.USER_NOT_FOUND + " " + studentId);
        }
        return student.get();
    }
}
