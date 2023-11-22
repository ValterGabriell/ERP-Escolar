package io.github.ValterGabriell.FrequenciaAlunos.controller;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Discipline;
import io.github.ValterGabriell.FrequenciaAlunos.dto.PatternResponse;
import io.github.ValterGabriell.FrequenciaAlunos.dto.discipline.CreateDiscipline;
import io.github.ValterGabriell.FrequenciaAlunos.service.DisciplineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/discipline")
@CrossOrigin(origins = "*")
public class DisciplineController {

    private final DisciplineService disciplineService;

    public DisciplineController(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @PostMapping(params = {"tenantId"})
    public ResponseEntity<PatternResponse<String>> insert(@RequestBody CreateDiscipline discipline, @RequestParam int tenantId) {
        PatternResponse<String> skid = disciplineService.insert(discipline, tenantId);
        return new ResponseEntity<>(skid, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{skid}", params = {"tenantId"})
    public ResponseEntity<String> update(@RequestBody Discipline discipline, @PathVariable String skid, @RequestParam("tenantId") int tenant) {
        String skidRet = disciplineService.update(discipline, skid, tenant);
        return new ResponseEntity<>(skidRet, HttpStatus.OK);
    }


    @GetMapping(params = {"tenantId"})
    public ResponseEntity<List<Discipline>> getAll(@RequestParam("tenantId") int tenant) {
        List<Discipline> lista = disciplineService.getAll(tenant);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }


    @GetMapping(value = "/{skid}", params = {"tenantId"})
    public ResponseEntity<Discipline> getById(@PathVariable("skid") String skid, @RequestParam("tenantId") int tenant) {
        Discipline discipline = disciplineService.getById(skid, tenant);
        return new ResponseEntity<>(discipline, HttpStatus.OK);
    }

    @DeleteMapping(value = "{skid}", params = "tenandId")
    public ResponseEntity<?> delete(@PathVariable String skid, @RequestParam int tenant) {
        disciplineService.delete(skid, tenant);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
