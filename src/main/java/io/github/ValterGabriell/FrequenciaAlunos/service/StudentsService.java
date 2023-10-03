package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Validation;
import io.github.ValterGabriell.FrequenciaAlunos.domain.admins.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.domain.days.Days;
import io.github.ValterGabriell.FrequenciaAlunos.domain.frequency.Frequency;
import io.github.ValterGabriell.FrequenciaAlunos.domain.students.Student;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.ExceptionsValues;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.AdminRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.FrequencyRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.StudentsRepository;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.students.InsertStudents;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.github.ValterGabriell.FrequenciaAlunos.exceptions.ExceptionsValues.STUDENT_ALREADY_SAVED;

@Service
public class StudentsService extends Validation {
    private final StudentsRepository studentsRepository;
    private final FrequencyRepository frequencyRepository;
    private final AdminRepository adminRepository;

    public StudentsService(StudentsRepository studentsRepository, FrequencyRepository frequencyRepository, AdminRepository adminRepository) {
        this.studentsRepository = studentsRepository;
        this.frequencyRepository = frequencyRepository;
        this.adminRepository = adminRepository;
    }

    /**
     * method to insert student on database
     *
     * @param request request with data to make the insertion
     *                should be: cpf with formatt: XXXXXXXXXXX
     *                cpf must contains 11 characters only with numbers
     *                besides that, student username that is a simple string that must contains only letters
     * @return student object created
     */
    public InsertStudents insertStudentIntoDatabase(InsertStudents request, String adminId) {
        boolean present = studentsRepository.findById(request.getCpf()).isPresent();

        if (present) {
            throw new RequestExceptions(STUDENT_ALREADY_SAVED);
        }
        if (request.usernameIsNull()
                && request.isFieldHasNumberExcatlyOfChars(request.getCpf(), 11)
                && request.usernameHasToBeMoreThanTwoChars()
                && request.emailIsNull()
        ) {
            Admin admin = checkIfStudentAlreadyInsertedToAdmin(adminId, request.getCpf());
            if (admin == null) {
                throw new RequestExceptions("Estudante já cadastrado para esse administrador!");
            } else {
                Student student;
                student = request.toModel();
                student.setAdmin(admin);

                Frequency frequency = new Frequency();
                frequency.setId(student.getCpf());
                frequency.setDaysList(new ArrayList<>());

                frequencyRepository.save(frequency);
                studentsRepository.save(student);
            }
        }
        return request;
    }

    private Admin checkIfStudentAlreadyInsertedToAdmin(String adminId, String studentId) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new RequestExceptions("Administrador não encontrado!"));
        for (Student student : admin.getStudents()) {
            if (student.getCpf().equals(studentId)) {
                return admin;
            }
        }
        if (admin.getStudents().isEmpty()){
            return admin;
        }
        return null;
    }

    /**
     * update student data
     *
     * @param request   request with new data to make the insertion should be: cpf with formatt: XXXXXXXXXXX cpf must contains 11 characters only with numbers besides that, student username that is a simple string that must contains only letters
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
                && request.usernameHasToBeMoreThanTwoChars()
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