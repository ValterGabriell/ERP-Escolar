package io.github.ValterGabriell.FrequenciaAlunos.domain.students;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Validation;
import io.github.ValterGabriell.FrequenciaAlunos.domain.days.Days;
import io.github.ValterGabriell.FrequenciaAlunos.domain.frequency.Frequency;
import io.github.ValterGabriell.FrequenciaAlunos.domain.students.dto.InsertStudents;
import io.github.ValterGabriell.FrequenciaAlunos.excpetion.ExceptionsValues;
import io.github.ValterGabriell.FrequenciaAlunos.excpetion.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.FrequencyRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.StudentsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.github.ValterGabriell.FrequenciaAlunos.excpetion.ExceptionsValues.STUDENT_ALREADY_SAVED;

@Service
public class StudentsService extends Validation {
    private final StudentsRepository studentsRepository;
    private final FrequencyRepository frequencyRepository;

    public StudentsService(StudentsRepository studentsRepository, FrequencyRepository frequencyRepository) {
        this.studentsRepository = studentsRepository;
        this.frequencyRepository = frequencyRepository;
    }

    /**
     * method to insert student on database
     * @param request request with data to make the insertion
     *                should be: cpf with formatt: XXXXXXXXXXX
     *                cpf must contains 11 characters only with numbers
     *                besides that, student username that is a simple string that must contains only letters
     * @return student object created
     */
    public Student insertStudentIntoDatabase(InsertStudents request) {
        boolean present = studentsRepository.findById(request.getCpf()).isPresent();
        if (present) {
            throw new RequestExceptions(STUDENT_ALREADY_SAVED);
        }
        Student student = request.toModel();
        if (request.usernameIsNull()
                && request.isFieldHasNumberExcatlyOfChars(request.getCpf(), 11)
                && request.usernameHasToBeMoreThan2Chars()) {
            Frequency frequency = new Frequency();
            frequency.setDaysList(new ArrayList<>());
            frequency.setId(request.getCpf());
            frequencyRepository.save(frequency);
            studentsRepository.save(student);
        }
        return student;
    }

    /**
     * update student data
     * @param request request with new data to make the insertion should be: cpf with formatt: XXXXXXXXXXX cpf must contains 11 characters only with numbers besides that, student username that is a simple string that must contains only letters
     * @param studentId id of student that wanna update
     * @return student updated
     */
    public Student updateStudent(InsertStudents request, String studentId) {
        /* get student to be updated */
        Student oldStudent = studentsRepository.findById(studentId).orElseThrow(() -> new RequestExceptions(ExceptionsValues.USER_NOT_FOUND));
        /* create new student object */
        Student newStudent = new Student();
        if (request.usernameIsNull()
                && request.isFieldHasNumberExcatlyOfChars(request.getCpf(), 11)
                && request.usernameHasToBeMoreThan2Chars()
                && request.fieldContainsOnlyLetters(request.getUsername())) {

            /* get the frequency of student to be updated */
            Frequency frequency = frequencyRepository
                    .findById(oldStudent.getCpf())
                    .orElseThrow(() -> new RequestExceptions(ExceptionsValues.FREQUENCY_NOT_FOUND));
            /* store the list of days from students in a variabel dayList */
            List<Days> daysList = frequency.getDaysList();
            /* delete the student frequency */
            frequencyRepository.delete(frequency);

            /* create a new frequency with the new id and old dayList */
            Frequency newFrequency = new Frequency();
            newFrequency.setId(request.getCpf());
            newFrequency.setDaysList(daysList);
            frequencyRepository.save(newFrequency);

            /* delete old student and create a new student with new data.
            * student id is matching frequency id */
            studentsRepository.delete(oldStudent);
            newStudent.setCpf(request.getCpf());
            newStudent.setUsername(request.getUsername());
            studentsRepository.save(newStudent);
        }
        return newStudent;
    }

    public List<Student> getAllStudentsFromDatabase() {
        List<Student> allStudents = studentsRepository.findAll();
        Collections.sort(allStudents);
        return allStudents;
    }

    public void deleteStudent(String studentId) {
        Student student = validateIfStudentExistsAndReturnIfExist(studentsRepository, studentId);
        studentsRepository.delete(student);
    }
}