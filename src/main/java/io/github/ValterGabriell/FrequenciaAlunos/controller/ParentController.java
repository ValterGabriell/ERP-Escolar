package io.github.ValterGabriell.FrequenciaAlunos.controller;

import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.dto.PatternResponse;
import io.github.ValterGabriell.FrequenciaAlunos.dto.parents.CreateParent;
import io.github.ValterGabriell.FrequenciaAlunos.dto.parents.ParentGet;
import io.github.ValterGabriell.FrequenciaAlunos.dto.parents.UpdateParent;
import io.github.ValterGabriell.FrequenciaAlunos.service.ParentsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/v1/parent")
@CrossOrigin(origins = "*")
public class ParentController {

    private final ParentsService parentsService;

    public ParentController(ParentsService parentsService) {
        this.parentsService = parentsService;
    }

    @PostMapping(value = "{adminCnpj}", params = {"tenantId"})
    public ResponseEntity<PatternResponse<String>> insert(
            @RequestBody CreateParent createParent,
            @RequestParam Integer tenantId,
            @PathVariable String adminCnpj) {
        PatternResponse<String> parent = parentsService.createParent(createParent, tenantId, adminCnpj);
        return new ResponseEntity<>(parent, HttpStatus.CREATED);
    }

    @GetMapping(value = {"/{skid}"}, params = {"tenantId"})
    public ResponseEntity<ParentGet> getBySkId(
            @PathVariable String skid,
            @RequestParam Integer tenantId) {
        ParentGet parentGet = parentsService.getBySkId(tenantId, skid);
        return new ResponseEntity<>(parentGet, HttpStatus.OK);
    }


    @GetMapping(params = {"tenantId"})
    public ResponseEntity<Page<ParentGet>> getAllParents(@RequestParam Integer tenantId, Pageable pageable) {
        var listAdmins = parentsService.getAllParents(tenantId, pageable);
        return new ResponseEntity<>(listAdmins, HttpStatus.OK);
    }

    @PutMapping(value = "update/{skid}", params = {"tenantId", "adminCnpj"})
    public ResponseEntity<String> updateParent(
            @PathVariable String skid,
            @RequestBody UpdateParent updateParent,
            @RequestParam Integer tenantId,
            @RequestParam String adminCnpj) throws RequestExceptions {
        String result = parentsService.updateParentFirstName(updateParent, tenantId, adminCnpj, skid);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{skid}", params = {"tenantId"})
    public ResponseEntity<?> deleteBySkId(@PathVariable String skid, @RequestParam Integer tenantId) {
        parentsService.deleteParentBySkId(tenantId, skid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
