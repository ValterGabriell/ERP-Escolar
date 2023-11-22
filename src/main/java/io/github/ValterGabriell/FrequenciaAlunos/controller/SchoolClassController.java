package io.github.ValterGabriell.FrequenciaAlunos.controller;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Professor;
import io.github.ValterGabriell.FrequenciaAlunos.domain.SchoolClass;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Student;
import io.github.ValterGabriell.FrequenciaAlunos.dto.PatternResponse;
import io.github.ValterGabriell.FrequenciaAlunos.dto.schoolclass.CreateSC;
import io.github.ValterGabriell.FrequenciaAlunos.service.SchoolClassService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/class")
@CrossOrigin(origins = "*")
public class SchoolClassController {
    private final SchoolClassService schoolClassService;

    public SchoolClassController(SchoolClassService schoolClassService) {
        this.schoolClassService = schoolClassService;
    }

    @PostMapping(value = "/{adminId}", params = {"tenant"})
    public ResponseEntity<PatternResponse<String>> create(
            @RequestBody CreateSC createSC, @PathVariable String adminId, @RequestParam int tenant) {
        PatternResponse<String> skid = schoolClassService.create(createSC, adminId, tenant);
        return new ResponseEntity<>(skid, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{skid}", params = {"tenant"})
    public ResponseEntity<SchoolClass> getBySkId(@PathVariable String skid, @RequestParam int tenant) {
        SchoolClass schoolClass = schoolClassService.getBySkId(skid, tenant);
        return new ResponseEntity<>(schoolClass, HttpStatus.OK);
    }

    @PatchMapping(value = "/student/{skid}", params = {"tenant", "studentSkId"})
    public ResponseEntity<PatternResponse<String>> setStudentToClass(@PathVariable String skid,
                                                                     @RequestParam int tenant,
                                                                     @RequestParam String studentSkId) {
        PatternResponse<String> schoolClass = schoolClassService.setStudentToClass(skid, tenant, studentSkId);
        return new ResponseEntity<>(schoolClass, HttpStatus.OK);
    }

    @PatchMapping(value = "/professor/{skid}", params = {"tenant", "professorSkId"})
    public ResponseEntity<PatternResponse<String>> setProfessorToClass(@PathVariable String skid,
                                                                     @RequestParam int tenant,
                                                                     @RequestParam String professorSkId) {
        PatternResponse<String> schoolClass = schoolClassService.setProfessorToClass(skid, tenant, professorSkId);
        return new ResponseEntity<>(schoolClass, HttpStatus.OK);
    }

    @GetMapping(value = "students/{skid}", params = {"tenant"})
    public ResponseEntity<List<Student>> getStudentsByClassSkId(@PathVariable String skid, @RequestParam int tenant) {
        List<Student> schoolClass = schoolClassService.getAllStudentsByClassSkId(skid, tenant);
        return new ResponseEntity<>(schoolClass, HttpStatus.OK);
    }

    @GetMapping(value = "professors/{skid}", params = {"tenant"})
    public ResponseEntity<List<Professor>> getAllProfessorsByClassSkId(@PathVariable String skid,
                                                                     @RequestParam int tenant) {
        List<Professor> schoolClass = schoolClassService.getAllProfessorsByClassSkId(skid, tenant);
        return new ResponseEntity<>(schoolClass, HttpStatus.OK);
    }

    @GetMapping(params = {"tenant"})
    public ResponseEntity<List<SchoolClass>> getAll(@RequestParam int tenant) {
        List<SchoolClass> schoolClasses = schoolClassService.getAll(tenant);
        return new ResponseEntity<>(schoolClasses, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{skid}", params = {"tenant"})
    public ResponseEntity<?> delete(@PathVariable String skid, @RequestParam int tenant) {
        schoolClassService.delete(skid, tenant);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
