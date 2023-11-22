package io.github.ValterGabriell.FrequenciaAlunos.controller;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Student;
import io.github.ValterGabriell.FrequenciaAlunos.dto.PatternResponse;
import io.github.ValterGabriell.FrequenciaAlunos.dto.students.GetStudent;
import io.github.ValterGabriell.FrequenciaAlunos.dto.students.InsertStudents;
import io.github.ValterGabriell.FrequenciaAlunos.service.StudentsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @RequestParam Integer tenant,
            @RequestParam(required = false) String parentIdentifier) {
        PatternResponse<String> student = service.insertStudentIntoDatabase(request, adminCnpj, tenant, parentIdentifier);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @GetMapping(params = {"tenant"})
    public ResponseEntity<List<Student>> getAllStudents(@RequestParam int tenant) {
        List<Student> allStudentsFromDatabase = service.getAllStudentsFromDatabase(tenant);
        return new ResponseEntity<>(allStudentsFromDatabase, HttpStatus.OK);
    }

    @GetMapping(value = "/{skid}", params = {"tenant"})
    public ResponseEntity<GetStudent> getStudentBySkId(@PathVariable String skid, @RequestParam int tenant) {
        GetStudent student = service.getStudentBySkId(skid, tenant);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @DeleteMapping(params = {"studentSkId", "tenant"})
    public ResponseEntity<?> deleteStudent(@RequestParam String studentSkId, @RequestParam int tenant) {
        service.deleteStudent(studentSkId, tenant);
        return ResponseEntity.noContent().build();
    }
}