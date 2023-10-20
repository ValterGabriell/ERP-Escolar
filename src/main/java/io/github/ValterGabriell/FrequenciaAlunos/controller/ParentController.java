package io.github.ValterGabriell.FrequenciaAlunos.controller;

import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.parents.CreateParent;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.parents.ParentGet;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.parents.UpdateParent;
import io.github.ValterGabriell.FrequenciaAlunos.service.ParentsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/v1/parent")
public class ParentController {

    private final ParentsService parentsService;

    public ParentController(ParentsService parentsService) {
        this.parentsService = parentsService;
    }

    @PostMapping(value = "{adminCnpj}", params = {"tenantId"})
    public ResponseEntity<String> insert(
            @RequestBody CreateParent createParent,
            @RequestParam Integer tenantId,
            @PathVariable String adminCnpj) {
        String parent = parentsService.createParent(createParent, tenantId, adminCnpj);
        return new ResponseEntity<>(parent, HttpStatus.CREATED);
    }

    @GetMapping(value = {"/{parentIdentifier}"}, params = {"tenantId"})
    public ResponseEntity<ParentGet> getParentByIdentifier(
            @PathVariable String parentIdentifier,
            @RequestParam Integer tenantId) {
        ParentGet parentGet = parentsService.getByIdentifierNumber(tenantId, parentIdentifier);
        return new ResponseEntity<>(parentGet, HttpStatus.OK);
    }


    @GetMapping(params = {"tenantId"})
    public ResponseEntity<Page<ParentGet>> getAllParents(@RequestParam Integer tenantId, Pageable pageable) {
        var listAdmins = parentsService.getAllParents(tenantId, pageable);
        return new ResponseEntity<>(listAdmins, HttpStatus.OK);
    }

    @PatchMapping(value = "update/{identifierNumber}", params = {"tenantId", "adminCnpj"})
    public ResponseEntity<String> updateParent(
            @PathVariable String identifierNumber,
            @RequestBody UpdateParent updateParent,
            @RequestParam Integer tenantId,
            @RequestParam String adminCnpj) throws RequestExceptions {
        String result = parentsService.updateParentFirstName(updateParent, tenantId, adminCnpj, identifierNumber);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{identifierNumber}", params = {"tenantId"})
    public ResponseEntity<?> deleteAdminByCnpj(@PathVariable String identifierNumber, @RequestParam Integer tenantId) {
        parentsService.deleteParentByIdentifierNumber(tenantId, identifierNumber);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
