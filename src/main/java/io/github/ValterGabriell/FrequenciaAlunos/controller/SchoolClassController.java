package io.github.ValterGabriell.FrequenciaAlunos.controller;

import io.github.ValterGabriell.FrequenciaAlunos.domain.school_class.SchoolClass;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.schoolclass.CreateSC;
import io.github.ValterGabriell.FrequenciaAlunos.service.SchoolClassService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/class")
public class SchoolClassController {
    private final SchoolClassService schoolClassService;

    public SchoolClassController(SchoolClassService schoolClassService) {
        this.schoolClassService = schoolClassService;
    }

    @PostMapping(value = "/{adminId}", params = {"tenantId"})
    public ResponseEntity<String> create(
            @RequestBody CreateSC createSC, @PathVariable String adminId, @RequestParam int tenantId) {
        String skid = schoolClassService.create(createSC, adminId, tenantId);
        return new ResponseEntity<>(skid, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{skid}", params = {"tenantId"})
    public ResponseEntity<SchoolClass> getBySkId(@PathVariable String skid, @RequestParam int tenantId) {
        SchoolClass schoolClass = schoolClassService.getBySkId(skid, tenantId);
        return new ResponseEntity<>(schoolClass, HttpStatus.OK);
    }

    @GetMapping(params = {"tenantId"})
    public ResponseEntity<List<SchoolClass>> getAll(@RequestParam int tenantId) {
        List<SchoolClass> schoolClasses = schoolClassService.getAll(tenantId);
        return new ResponseEntity<>(schoolClasses, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{skid}", params = {"tenantId"})
    public ResponseEntity<?> delete(@PathVariable String skid, @RequestParam int tenantId) {
        schoolClassService.delete(skid,tenantId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
