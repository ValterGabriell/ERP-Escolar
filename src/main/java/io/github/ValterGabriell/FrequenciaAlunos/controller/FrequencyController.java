package io.github.ValterGabriell.FrequenciaAlunos.controller;

import io.github.ValterGabriell.FrequenciaAlunos.service.FrequencyService;
import io.github.ValterGabriell.FrequenciaAlunos.domain.frequency.dto.ResponseDaysThatStudentGoToClass;
import io.github.ValterGabriell.FrequenciaAlunos.domain.frequency.dto.ResponseValidateFrequency;
import io.github.ValterGabriell.FrequenciaAlunos.domain.sheet.dto.ResponseSheet;
import io.github.ValterGabriell.FrequenciaAlunos.excpetion.RequestExceptions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("frequency")
public class FrequencyController {
    private final FrequencyService frequencyService;


    public FrequencyController(FrequencyService frequencyService) {
        this.frequencyService = frequencyService;
    }
    @Secured("ADMIN")
    @PostMapping(params = {"studentId"})
    public ResponseEntity<ResponseValidateFrequency> validateFrequency(@RequestParam String studentId) throws RequestExceptions {
        ResponseValidateFrequency responseValidadeFrequency = frequencyService.validateFrequency(studentId);
        return new ResponseEntity<>(responseValidadeFrequency, HttpStatus.OK);
    }
    @Secured("ADMIN")
    @PostMapping(params = {"studentId", "date"})
    public ResponseEntity<ResponseValidateFrequency> justifyAbsence(@RequestParam String studentId, @RequestParam LocalDate date) throws RequestExceptions {
        ResponseValidateFrequency responseValidadeFrequency = frequencyService.justifyAbsence(date, studentId);
        return new ResponseEntity<>(responseValidadeFrequency, HttpStatus.OK);
    }
    @Secured("ADMIN")
    @PutMapping(params = {"studentId", "date"})
    public ResponseEntity<ResponseValidateFrequency> updateAbscence(@RequestParam String studentId, @RequestParam LocalDate date) throws RequestExceptions {
        ResponseValidateFrequency responseValidadeFrequency = frequencyService.updateAbscence(date, studentId);
        return new ResponseEntity<>(responseValidadeFrequency, HttpStatus.OK);
    }
    @Secured("ADMIN")
    @GetMapping(params = {"studentId"})
    public ResponseEntity<ResponseDaysThatStudentGoToClass> getListOfDays(@RequestParam String studentId) throws RequestExceptions {
        ResponseDaysThatStudentGoToClass listOfDaysByFrequencyId = frequencyService.getListOfDaysByFrequencyId(studentId);
        return new ResponseEntity<>(listOfDaysByFrequencyId, HttpStatus.OK);
    }
    @Secured("ADMIN")
    @GetMapping(value = "sheet")
    public ResponseEntity<?> createSheet() {
        ResponseSheet responseSheet = frequencyService.createSheetForCurrentDay();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" + responseSheet.getSheetName())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(responseSheet.getSheetByteArray());
    }
    @Secured("ADMIN")
    @GetMapping(value = "sheet", params = {"date"})
    public ResponseEntity<?> getSheetForSpecifyDay(@RequestParam LocalDate date) {
        ResponseSheet responseSheet = frequencyService.returnSheetForSpecifyDay(date);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" + responseSheet.getSheetName())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(responseSheet.getSheetByteArray());
    }
}
