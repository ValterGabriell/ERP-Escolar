package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Day;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Frequency;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Student;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.DaysRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.FrequencyRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.SchoolClassesRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.StudentsRepository;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.frequency.JustifyAbscenceDesc;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.frequency.ResponseDaysThatStudentGoToClass;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.frequency.ResponseValidateFrequency;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.sheets.ResponseSheet;
import io.github.ValterGabriell.FrequenciaAlunos.util.GenerateSKId;
import io.github.ValterGabriell.FrequenciaAlunos.util.sheet.SheetManipulation;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.ExceptionsValues;
import io.github.ValterGabriell.FrequenciaAlunos.validation.FieldValidation;
import io.github.ValterGabriell.FrequenciaAlunos.validation.FrequencyValidation;
import io.github.ValterGabriell.FrequenciaAlunos.validation.StudentValidation;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FrequencyService extends FrequencyValidation {


    private final StudentsRepository studentsRepository;
    private final DaysRepository daysRepository;
    private final FrequencyRepository frequencyRepository;
    private final FieldValidation fieldValidation = new FieldValidation();
    private final StudentValidation studentValidation = new StudentValidation();
    private final FrequencyValidation frequencyValidation = new FrequencyValidation();

    public FrequencyService(StudentsRepository studentsRepository, DaysRepository daysRepository, FrequencyRepository frequencyRepository) {
        this.studentsRepository = studentsRepository;
        this.daysRepository = daysRepository;
        this.frequencyRepository = frequencyRepository;
    }


    /**
     * method that validate student frequency
     *
     * @param studentSkId represent primary key of student table
     * @return response with the student frequency validated or erro while validation frequency
     */
    public ResponseValidateFrequency validateFrequency(String studentSkId, int tenantId) throws RequestExceptions {

        StudentValidation studentValidation = new StudentValidation();
        Student student = studentValidation
                .validateIfStudentExistsAndReturnIfExist(studentsRepository, studentSkId, tenantId);

        Frequency frequency = frequencyRepository.findById(student.getStudentId()).get();

        Day currentDay = new Day(LocalDate.now(), frequency.getTenant());
        verifyIfDayAlreadySavedOnFrequencyAndThrowAnErroIfItIs(frequency, currentDay);
        currentDay.setSkid(GenerateSKId.generateSkId());
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

        StudentValidation studentValidation = new StudentValidation();
        Student student = studentValidation
                .validateIfStudentExistsAndReturnIfExist(studentsRepository, studentId, tenantId);
        Frequency frequency = frequencyRepository.findById(student.getStudentId()).get();
        ResponseDaysThatStudentGoToClass responseDaysThatStudentGoToClass = new ResponseDaysThatStudentGoToClass();
        responseDaysThatStudentGoToClass.setStudentSkId(student.getSkid());

        List<Day> dayList = frequency.getDaysList();
        responseDaysThatStudentGoToClass.setDaysListThatStudentGoToClass(dayList);
        return responseDaysThatStudentGoToClass;
    }

    /*
    create sheet for current day and download it
     */
    public ResponseSheet createSheetForCurrentDay(int tenant) {
        ResponseSheet responseSheet = new ResponseSheet();
        SheetManipulation sheetManipulation = new SheetManipulation();
        List<Student> students = studentsRepository.findAllByTenant(tenant);
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
    public ResponseSheet returnSheetForSpecifyDay(LocalDate date, int tenant) {
        SheetManipulation sheetManipulation = new SheetManipulation();
        ResponseSheet responseSheet = new ResponseSheet();
        List<Student> students = studentsRepository
                .findAllByTenant(tenant)
                .stream()
                .filter(student -> frequencyRepository.findById(student.getStudentId())
                        .get().getDaysList().stream()
                        .anyMatch(_day -> _day.getDate().equals(date)))
                .collect(Collectors.toList());
        responseSheet.setSheetName("Planilha do dia " +
                date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)) + ".xls");
        responseSheet.setSheetByteArray(sheetManipulation.createSheet(students, date));
        return responseSheet;
    }

    /**
     * method to justify abscence of student on some class
     *
     * @param date       date to validate student present
     * @param studentkId student to be justified
     * @return string with message
     */
    public ResponseValidateFrequency justifyAbsence(JustifyAbscenceDesc justifyAbscenceDesc
            , LocalDate date, String studentkId, int tenant) {

        StudentValidation studentValidation = new StudentValidation();
        Student student = studentValidation
                .validateIfStudentExistsAndReturnIfExist(studentsRepository, studentkId, tenant);
        Frequency frequency = frequencyRepository.findById(student.getStudentId()).get();
        Day day = new Day(date, frequency.getTenant());
        verifyIfDayAlreadySavedOnFrequencyAndThrowAnErroIfItIs(frequency, day);


        if (justifyAbscenceDesc.getDescription().isEmpty() || justifyAbscenceDesc.getDescription().isBlank())
            throw new RequestExceptions("Descrição de justificativa precisa ser passada");


        day.setJustified(true);
        day.setSkid(GenerateSKId.generateSkId());
        day.setDescription(justifyAbscenceDesc.getDescription());
        frequency.getDaysList().add(day);
        frequencyRepository.save(frequency);

        ResponseValidateFrequency responseValidateFrequency = new ResponseValidateFrequency();
        responseValidateFrequency.setMessage("Frequência para "
                + student.getFirstName() + " justificada! - Dia: "
                + date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
        return responseValidateFrequency;
    }

    /**
     * update the justified field changing it from true to false
     *
     * @param date        date to change the student present
     * @param studentSkId student to be justified
     * @param tenant
     * @return string with message
     */
    public ResponseValidateFrequency updateAbscence(LocalDate date, String studentSkId, int tenant) {
        StudentValidation studentValidation = new StudentValidation();
        Student student = studentValidation
                .validateIfStudentExistsAndReturnIfExist(studentsRepository, studentSkId, tenant);
        Frequency frequency = frequencyRepository.findById(student.getStudentId()).get();
        Day dayFounded = frequency.getDaysList()
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


    public List<Day> getDaysThatStudentsWatchedSchoolClassesInASpecificMonth(String studentSkId,
                                                                             String month,
                                                                             int tenant) {
        fieldValidation.validateIfIsNotEmpty(studentSkId, "SKid do Estudante não pode estar nulo!");
        fieldValidation.validateIfIsNotEmpty(month, "Mês não pode estar nulo!");
        // studentValidation.validateIfStudentExistsAndReturnIfExist(studentsRepository, studentSkId, tenant);

        Frequency frequency =
                frequencyRepository.findBySkidAndTenant(studentSkId, tenant);
        boolean exists = frequencyValidation.verifyIfFrequencyExists(frequencyRepository, studentSkId, tenant);
        if (!exists) throw new RequestExceptions("Frequencia nao encontrada");
        boolean validated = frequencyValidation.validateMonth(month);
        if (!validated) throw new RequestExceptions("Insira um mês válido", Arrays.toString(Month.values()));
        return frequency
                .getDaysList()
                .stream()
                .filter(date -> date.getDate().getMonth().name().equals(month))
                .toList();
    }
}