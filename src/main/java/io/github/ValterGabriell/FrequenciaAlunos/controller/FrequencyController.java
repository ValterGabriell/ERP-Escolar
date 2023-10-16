package io.github.ValterGabriell.FrequenciaAlunos.controller;

import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
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

@RestController
@RequestMapping("/api/v1/frequency")
public class FrequencyController {
    private final FrequencyService frequencyService;


    public FrequencyController(FrequencyService frequencyService) {
        this.frequencyService = frequencyService;
    }

    @PostMapping(params = {"studentId", "tenantId"})
    public ResponseEntity<ResponseValidateFrequency> validateFrequency(@RequestParam String studentId,
                                                                       @RequestParam int tenantId) throws RequestExceptions {
        ResponseValidateFrequency responseValidadeFrequency = frequencyService.validateFrequency(studentId, tenantId);
        return new ResponseEntity<>(responseValidadeFrequency, HttpStatus.OK);
    }

    @PostMapping(params = {"studentId", "date"})
    public ResponseEntity<ResponseValidateFrequency> justifyAbsence(@RequestParam String studentId, @RequestParam LocalDate date) throws RequestExceptions {
        ResponseValidateFrequency responseValidadeFrequency = frequencyService.justifyAbsence(date, studentId);
        return new ResponseEntity<>(responseValidadeFrequency, HttpStatus.OK);
    }

    @PutMapping(params = {"studentId", "date"})
    public ResponseEntity<ResponseValidateFrequency> updateAbscence(@RequestParam String studentId, @RequestParam LocalDate date) throws RequestExceptions {
        ResponseValidateFrequency responseValidadeFrequency = frequencyService.updateAbscence(date, studentId);
        return new ResponseEntity<>(responseValidadeFrequency, HttpStatus.OK);
    }

    @GetMapping(params = {"studentId","tenantId"})
    public ResponseEntity<ResponseDaysThatStudentGoToClass> getListOfDays(@RequestParam String studentId,
                                                                          @RequestParam int tenantId) throws RequestExceptions {
        ResponseDaysThatStudentGoToClass listOfDaysByFrequencyId = frequencyService.getListOfDaysByFrequencyId(studentId,tenantId);
        return new ResponseEntity<>(listOfDaysByFrequencyId, HttpStatus.OK);
    }

    @GetMapping(value = "sheet")
    public ResponseEntity<?> createSheet() {
        ResponseSheet responseSheet = frequencyService.createSheetForCurrentDay();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" + responseSheet.getSheetName())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(responseSheet.getSheetByteArray());
    }

    @GetMapping(value = "sheet", params = {"date"})
    public ResponseEntity<?> getSheetForSpecifyDay(@RequestParam LocalDate date) {
        ResponseSheet responseSheet = frequencyService.returnSheetForSpecifyDay(date);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" + responseSheet.getSheetName())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(responseSheet.getSheetByteArray());
    }
}
