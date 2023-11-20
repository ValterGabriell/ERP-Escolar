package io.github.ValterGabriell.FrequenciaAlunos.controller;

import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.dto.PatternResponse;
import io.github.ValterGabriell.FrequenciaAlunos.dto.professor.CreateProfessor;
import io.github.ValterGabriell.FrequenciaAlunos.dto.professor.ProfessorGet;
import io.github.ValterGabriell.FrequenciaAlunos.dto.professor.UpdateProfessor;
import io.github.ValterGabriell.FrequenciaAlunos.service.ProfessorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/v1/professor")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }


    @PostMapping(value = "{adminCnpj}", params = {"tenantId"})
    public ResponseEntity<PatternResponse<String>> insert(
            @RequestBody CreateProfessor createProfessor,
            @RequestParam Integer tenantId,
            @PathVariable String adminCnpj
    ) {

        PatternResponse<String> professor = professorService.createProfessor(createProfessor, adminCnpj, tenantId);
        return new ResponseEntity<>(professor, HttpStatus.CREATED);
    }

    @GetMapping(value = {"/{skid}"}, params = {"tenantId"})
    public ResponseEntity<ProfessorGet> getByIdentifier(
            @PathVariable String skid,
            @RequestParam Integer tenantId) {
        ProfessorGet bySkId = professorService.getBySkId(tenantId, skid);
        return new ResponseEntity<>(bySkId, HttpStatus.OK);
    }


    @GetMapping(params = {"tenantId"})
    public ResponseEntity<Page<ProfessorGet>> getAll(@RequestParam Integer tenantId, Pageable pageable) {
        Page<ProfessorGet> allProfessors = professorService.getAllProfessors(tenantId, pageable);
        return new ResponseEntity<>(allProfessors, HttpStatus.OK);
    }

    @PutMapping(value = "update/{skid}", params = {"tenantId"})
    public ResponseEntity<String> update(
            @PathVariable String skid,
            @RequestBody UpdateProfessor updateProfessor,
            @RequestParam Integer tenantId) throws RequestExceptions {
        String s = professorService.updateProfessor(updateProfessor, tenantId, skid);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{skid}", params = {"tenantId"})
    public ResponseEntity<?> delete(@PathVariable String skid, @RequestParam Integer tenantId) {
        professorService.deleteProfessorByIdentifierNumber(tenantId, skid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
