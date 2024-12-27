package io.github.ValterGabriell.FrequenciaAlunos.controller;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Professor;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Salas;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Turma;
import io.github.ValterGabriell.FrequenciaAlunos.dto.PatternResponse;
import io.github.ValterGabriell.FrequenciaAlunos.dto.schoolclass.CreateSC;
import io.github.ValterGabriell.FrequenciaAlunos.service.SalasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping()
public class SchoolClassController {
    private final SalasService salasService;

    public SchoolClassController(SalasService salasService) {
        this.salasService = salasService;
    }

    @PostMapping(value = "/api/v1/class", params = {"tenant", "adminId", "salaName"})
    public ResponseEntity<PatternResponse<String>> create(
            @RequestParam String adminId, @RequestParam int tenant, @RequestParam String salaName) {

        CreateSC cs = new CreateSC(salaName);
        PatternResponse<String> skid = salasService.create(cs, adminId, tenant);
        return new ResponseEntity<>(skid, HttpStatus.CREATED);
    }

    @GetMapping(value = "/api/v1/class/{skid}", params = {"tenant"})
    public ResponseEntity<Salas> getBySkId(@PathVariable String skid, @RequestParam int tenant) {
        Salas salas = salasService.getBySkId(skid, tenant);
        return new ResponseEntity<>(salas, HttpStatus.OK);
    }


    @PatchMapping(value = "/api/v1/class/professor/{skid}", params = {"tenant", "professorSkId"})
    public ResponseEntity<PatternResponse<String>> setProfessorToClass(@PathVariable String skid,
                                                                     @RequestParam int tenant,
                                                                     @RequestParam String professorSkId) {
        PatternResponse<String> schoolClass = salasService.setProfessorToClass(skid, tenant, professorSkId);
        return new ResponseEntity<>(schoolClass, HttpStatus.OK);
    }
    @GetMapping(value = "/api/v1/class/professors/{skid}", params = {"tenant"})
    public ResponseEntity<List<Professor>> getAllProfessorsByClassSkId(@PathVariable String skid,
                                                                     @RequestParam int tenant) {
        List<Professor> schoolClass = salasService.getAllProfessorsByClassSkId(skid, tenant);
        return new ResponseEntity<>(schoolClass, HttpStatus.OK);
    }

    @GetMapping(value = "/api/v1/class/",params = {"tenant"})
    public ResponseEntity<List<Salas>> getAll(@RequestParam int tenant) {
        List<Salas> salas = salasService.getAll(tenant);
        return new ResponseEntity<>(salas, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/v1/class/{skid}", params = {"tenant"})
    public ResponseEntity<?> delete(@PathVariable String skid, @RequestParam int tenant) {
        salasService.delete(skid, tenant);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
