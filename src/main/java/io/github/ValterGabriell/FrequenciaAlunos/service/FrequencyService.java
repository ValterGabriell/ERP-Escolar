package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.domain.days.Days;
import io.github.ValterGabriell.FrequenciaAlunos.domain.frequency.Frequency;
import io.github.ValterGabriell.FrequenciaAlunos.domain.students.Student;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.DaysRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.FrequencyRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.StudentsRepository;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.frequency.ResponseDaysThatStudentGoToClass;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.frequency.ResponseValidateFrequency;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.sheets.ResponseSheet;
import io.github.ValterGabriell.FrequenciaAlunos.util.sheet.SheetManipulation;
import io.github.ValterGabriell.FrequenciaAlunos.validation.ExceptionsValues;
import io.github.ValterGabriell.FrequenciaAlunos.validation.Validation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FrequencyService extends Validation {


    private final StudentsRepository studentsRepository;
    private final DaysRepository daysRepository;
    private final FrequencyRepository frequencyRepository;

    public FrequencyService(StudentsRepository studentsRepository, DaysRepository daysRepository, FrequencyRepository frequencyRepository) {
        this.studentsRepository = studentsRepository;
        this.daysRepository = daysRepository;
        this.frequencyRepository = frequencyRepository;
    }

    /**
     * method that validate student frequency
     *
     * @param studentId represent primary key of student table
     * @return response with the student frequency validated or erro while validation frequency
     */
    public ResponseValidateFrequency validateFrequency(String studentId, int tenantId) throws RequestExceptions {
        checkIfStudentCpfAreCorrectAndThrowExceptionIfItIs(studentId);

        Student student = validateIfStudentExistsAndReturnIfExist(studentsRepository, studentId, tenantId);

        Frequency frequency = frequencyRepository.findById(student.getId()).get();

        Days currentDay = new Days(LocalDate.now(), frequency.getTenant());

        verifyIfDayAlreadySavedOnFrequencyAndThrowAnErroIfItIs(frequency, currentDay);

        frequency.getDaysList().add(currentDay);

        frequencyRepository.save(frequency);

        ResponseValidateFrequency responseValidateFrequency = new ResponseValidateFrequency();
        responseValidateFrequency.setMessage("Frequência para " + student.getFirstName() + " válidada! - Dia: " + LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
        return responseValidateFrequency;
    }

    /**
     * return list with days that specific student watched class
     *
     * @param studentId represent primary key of student table
     */
    public ResponseDaysThatStudentGoToClass getListOfDaysByFrequencyId(String studentId, int tenantId) throws RequestExceptions {
        checkIfStudentCpfAreCorrectAndThrowExceptionIfItIs(studentId);
        Student student = validateIfStudentExistsAndReturnIfExist(studentsRepository, studentId, tenantId);
        Frequency frequency = frequencyRepository.findById(student.getId()).get();
        ResponseDaysThatStudentGoToClass responseDaysThatStudentGoToClass = new ResponseDaysThatStudentGoToClass();
        responseDaysThatStudentGoToClass.setStudentId(student.getId());

        List<Days> daysList = frequency.getDaysList();
        responseDaysThatStudentGoToClass.setDaysListThatStudentGoToClass(daysList);
        return responseDaysThatStudentGoToClass;
    }

    /*
    create sheet for current day and download it
     */
    public ResponseSheet createSheetForCurrentDay() {
        ResponseSheet responseSheet = new ResponseSheet();
        SheetManipulation sheetManipulation = new SheetManipulation();
        List<Student> students = studentsRepository.findAll();
        responseSheet.setSheetName("Planilha do dia " + LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)) + ".xls");
        responseSheet.setSheetByteArray(sheetManipulation.createSheet(students));
        return responseSheet;
    }

    /**
     * create sheet for specified date
     *
     * @param date that represent date to create sheet
     * @return sheet download
     */
    public ResponseSheet returnSheetForSpecifyDay(LocalDate date) {
        SheetManipulation sheetManipulation = new SheetManipulation();
        ResponseSheet responseSheet = new ResponseSheet();
        List<Student> students = studentsRepository
                .findAll()
                .stream()
                .filter(student -> frequencyRepository.findById(student.getId()).get().getDaysList().stream().anyMatch(_day -> _day.getDate().equals(date)))
                .collect(Collectors.toList());
        responseSheet.setSheetName("Planilha do dia " + date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)) + ".xls");
        responseSheet.setSheetByteArray(sheetManipulation.createSheet(students, date));
        return responseSheet;
    }

    /**
     * method to justify abscence of student on some class
     *
     * @param date      date to validate student present
     * @param studentId student to be justified
     * @return string with message
     */
    public ResponseValidateFrequency justifyAbsence(LocalDate date, String studentId) {
        Student student = studentsRepository.findById(studentId).orElseThrow(() -> new RequestExceptions(ExceptionsValues.USER_NOT_FOUND));
        Frequency frequency = frequencyRepository.findById(student.getId()).get();
        Days day = new Days(date, frequency.getTenant());
        verifyIfDayAlreadySavedOnFrequencyAndThrowAnErroIfItIs(frequency, day);

        day.setJustified(true);
        frequency.getDaysList().add(day);
        frequencyRepository.save(frequency);

        ResponseValidateFrequency responseValidateFrequency = new ResponseValidateFrequency();
        responseValidateFrequency.setMessage("Frequência para " + student.getFirstName() + " justificada! - Dia: " + date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
        return responseValidateFrequency;
    }

    /**
     * update the justified field changing it from true to false
     *
     * @param date      date to change the student present
     * @param studentId student to be justified
     * @return string with message
     */
    public ResponseValidateFrequency updateAbscence(LocalDate date, String studentId) {
        Student student = studentsRepository.findById(studentId).orElseThrow(() -> new RequestExceptions(ExceptionsValues.USER_NOT_FOUND));
        Frequency frequency = frequencyRepository.findById(student.getId()).get();
        Days dayFounded = frequency.getDaysList()
                .stream()
                .filter(days -> days.getDate().equals(date))
                .findFirst()
                .orElseThrow(() -> new RequestExceptions(ExceptionsValues.DAY_NOT_FOUND));

        frequency.getDaysList().remove(dayFounded);
        daysRepository.save(dayFounded);

        ResponseValidateFrequency responseValidateFrequency = new ResponseValidateFrequency();
        responseValidateFrequency.setMessage("Justificativa para " + student.getFirstName() + " atualizada! - Dia: " + date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
        return responseValidateFrequency;
    }
}