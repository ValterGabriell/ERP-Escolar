package io.github.ValterGabriell.FrequenciaAlunos.domain.students;

import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.StudentsRepository;

public interface StudentValidation {

    Student validateIfStudentExistsAndReturnIfExist(StudentsRepository studentsRepository, String studentId);
    void checkIfStudentCpfAreCorrect(String cpf);
}