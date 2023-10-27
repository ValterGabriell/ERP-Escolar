package io.github.ValterGabriell.FrequenciaAlunos.controller;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Day;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.frequency.JustifyAbscenceDesc;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.frequency.ResponseDaysThatStudentGoToClass;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.frequency.ResponseValidateFrequency;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.sheets.ResponseSheet;
import io.github.ValterGabriell.FrequenciaAlunos.service.FrequencyService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@RestController
@RequestMapping("/api/v1/frequency")
public class FrequencyController {
    private final FrequencyService frequencyService;

    public FrequencyController(FrequencyService frequencyService) {
        this.frequencyService = frequencyService;
    }

    @PostMapping(params = {"studentSkId", "tenant"})
    public ResponseEntity<ResponseValidateFrequency> validateFrequency(@RequestParam String studentSkId,
                                                                       @RequestParam int tenant) throws RequestExceptions {
        ResponseValidateFrequency responseValidadeFrequency = frequencyService.validateFrequency(studentSkId, tenant);
        return new ResponseEntity<>(responseValidadeFrequency, HttpStatus.OK);
    }

    @PostMapping(params = {"studentSkId", "date", "tenant"})
    public ResponseEntity<ResponseValidateFrequency> justifyAbsence(
            @RequestBody JustifyAbscenceDesc justifyAbscenceDesc,
            @RequestParam String studentSkId,
            @RequestParam LocalDate date,
            @RequestParam int tenant)
            throws RequestExceptions {
        ResponseValidateFrequency responseValidadeFrequency = frequencyService
                .justifyAbsence(justifyAbscenceDesc, date, studentSkId, tenant);
        return new ResponseEntity<>(responseValidadeFrequency, HttpStatus.OK);
    }

    @PutMapping(params = {"studentSkId", "date", "tenant"})
    public ResponseEntity<ResponseValidateFrequency> updateAbscence(
            @RequestParam String studentSkId, @RequestParam LocalDate date, @RequestParam int tenant) throws RequestExceptions {
        ResponseValidateFrequency responseValidadeFrequency = frequencyService.updateAbscence(date, studentSkId, tenant);
        return new ResponseEntity<>(responseValidadeFrequency, HttpStatus.OK);
    }

    @GetMapping(params = {"studentSkId", "tenant"})
    public ResponseEntity<ResponseDaysThatStudentGoToClass> getListOfDays(@RequestParam String studentSkId,
                                                                          @RequestParam int tenant) throws RequestExceptions {
        ResponseDaysThatStudentGoToClass listOfDaysByFrequencyId = frequencyService.getListOfDaysByFrequencyId(studentSkId, tenant);
        return new ResponseEntity<>(listOfDaysByFrequencyId, HttpStatus.OK);
    }

    @GetMapping(value = "sheet", params = "tenant")
    public ResponseEntity<?> createSheet(@RequestParam int tenant) {
        ResponseSheet responseSheet = frequencyService.createSheetForCurrentDay(tenant);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" + responseSheet.getSheetName())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(responseSheet.getSheetByteArray());
    }

    @GetMapping(value = "sheet", params = {"date", "tenant"})
    public ResponseEntity<?> getSheetForSpecifyDay(@RequestParam LocalDate date, @RequestParam int tenant) {
        ResponseSheet responseSheet = frequencyService.returnSheetForSpecifyDay(date, tenant);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" + responseSheet.getSheetName())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(responseSheet.getSheetByteArray());
    }

    @GetMapping(value = "/month/{month}")
    public ResponseEntity<List<Day>> getDaysThatStudentsWatchedSchoolClassesInASpecificMonth(@RequestParam String studentSkId,
                                                                                             @PathVariable String month,
                                                                                             @RequestParam int tenant) {
        List<Day> list = frequencyService
                .getDaysThatStudentsWatchedSchoolClassesInASpecificMonth(studentSkId, month, tenant);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
