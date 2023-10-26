package io.github.ValterGabriell.FrequenciaAlunos.controller;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Average;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.average.InsertAverage;
import io.github.ValterGabriell.FrequenciaAlunos.service.AverageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/average")
public class AverageController {


    private final AverageService averageService;


    public AverageController(AverageService averageService) {
        this.averageService = averageService;
    }

    @PostMapping(params = {"tenant"})
    public ResponseEntity<String> insert(@RequestBody InsertAverage insertAverage, @RequestParam int tenant) {
        String inserted = averageService.insert(insertAverage, tenant);
        return new ResponseEntity<>(inserted, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{studentSkId}", params = {"tenant"})
    public ResponseEntity<List<Average>> getAverageByStudent(
            @PathVariable String studentSkId, @RequestParam int tenant) {
        List<Average> averageByStudent = averageService.getAverageByStudent(studentSkId, tenant);
        return new ResponseEntity<>(averageByStudent, HttpStatus.OK);
    }

    @GetMapping(value = "/{studentSkId}/total", params = {"tenant"})
    public ResponseEntity<String> totalAverageByStudent(
            @PathVariable String studentSkId, @RequestParam int tenant) {
        String averageByStudent = averageService.totalAverageByStudent(studentSkId, tenant);
        return new ResponseEntity<>(averageByStudent, HttpStatus.OK);
    }

}
