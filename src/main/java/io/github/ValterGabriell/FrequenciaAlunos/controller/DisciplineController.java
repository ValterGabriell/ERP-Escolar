package io.github.ValterGabriell.FrequenciaAlunos.controller;

import io.github.ValterGabriell.FrequenciaAlunos.domain.discipline.Discipline;
import io.github.ValterGabriell.FrequenciaAlunos.service.DisciplineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/discipline")
public class DisciplineController {

    private final DisciplineService disciplineService;

    public DisciplineController(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @PostMapping
    public ResponseEntity<String> insert(@RequestBody Discipline discipline) {
        String skid = disciplineService.insert(discipline);
        return new ResponseEntity<>(skid, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{id}",params = {"tenantId"})
    public ResponseEntity<String> update(@RequestBody Discipline discipline, @PathVariable String id, @RequestParam("tenantId") int tenant) {
        String skid = disciplineService.update(discipline, id, tenant);
        return new ResponseEntity<>(skid, HttpStatus.OK);
    }


    @GetMapping(params = {"tenantId"})
    public ResponseEntity<List<Discipline>> getAll(@RequestParam("tenantId") int tenant) {
        List<Discipline> lista = disciplineService.getAll(tenant);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }


    @GetMapping(value = "/{id}", params = {"tenantId"})
    public ResponseEntity<Discipline> getById(@PathVariable("id") String id, @RequestParam("tenantId") int tenant) {
        Discipline discipline = disciplineService.getById(id, tenant);
        return new ResponseEntity<>(discipline, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}", params = "tenandId")
    public ResponseEntity<?> delete(@PathVariable String id,@RequestParam int tenant) {
        disciplineService.delete(id, tenant);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
