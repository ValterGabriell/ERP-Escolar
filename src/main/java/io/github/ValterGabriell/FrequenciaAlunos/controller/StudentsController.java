package io.github.ValterGabriell.FrequenciaAlunos.controller;

import io.github.ValterGabriell.FrequenciaAlunos.dto.PatternResponse;
import io.github.ValterGabriell.FrequenciaAlunos.dto.students.GetStudent;
import io.github.ValterGabriell.FrequenciaAlunos.dto.students.InsertStudents;
import io.github.ValterGabriell.FrequenciaAlunos.service.StudentsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/students")
public class StudentsController {
    private final StudentsService service;

    public StudentsController(StudentsService service) {
        this.service = service;
    }

    @PostMapping(value = "/{adminCnpj}")
    public ResponseEntity<PatternResponse<String>> insertStudentsIntoDatabase(
            @RequestBody InsertStudents request,
            @PathVariable String adminCnpj,
            @RequestParam Integer tenantId,
            @RequestParam String parentIdentifier) {
        PatternResponse<String> student = service.insertStudentIntoDatabase(request, adminCnpj, tenantId, parentIdentifier);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @GetMapping(params = {"tenantId"})
    public ResponseEntity<Page<GetStudent>> getAllStudents(Pageable pageable, @RequestParam int tenantId) {
        Page<GetStudent> allStudentsFromDatabase = service.getAllStudentsFromDatabase(pageable, tenantId);
        return new ResponseEntity<>(allStudentsFromDatabase, HttpStatus.OK);
    }

    @GetMapping(value = "/{skid}", params = {"tenantId"})
    public ResponseEntity<GetStudent> getStudentBySkId(@PathVariable String skid, @RequestParam int tenantId) {
        GetStudent student = service.getStudentBySkId(skid, tenantId);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @DeleteMapping(params = {"studentId", "tenantId"})
    public ResponseEntity<?> deleteStudent(@RequestParam String studentId, @RequestParam int tenantId) {
        service.deleteStudent(studentId, tenantId);
        return ResponseEntity.noContent().build();
    }
}