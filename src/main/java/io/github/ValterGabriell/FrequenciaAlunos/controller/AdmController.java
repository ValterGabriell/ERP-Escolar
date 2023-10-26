package io.github.ValterGabriell.FrequenciaAlunos.controller;


import io.github.ValterGabriell.FrequenciaAlunos.domain.admins.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.domain.professors.Professor;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.admin.*;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.professor.ProfessorGet;
import io.github.ValterGabriell.FrequenciaAlunos.service.AdmService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;

@RestController()
@RequestMapping("/api/v1/admin")
public class AdmController {
    private final AdmService adminService;

    public AdmController(AdmService adminService) {
        this.adminService = adminService;
    }

    @PostMapping(params = {"tenant"})
    public ResponseEntity<String> insertAdmin(@RequestBody CreateNewAdmin insertAdmin, @RequestParam Integer tenant) {
        var newAdmin = adminService.createNewAdmin(insertAdmin, tenant);
        return new ResponseEntity<>(newAdmin, HttpStatus.CREATED);
    }

    @GetMapping(value = {"/{cnpj}"}, params = {"tenant"})
    public ResponseEntity<GetAdminMapper> getAdminByCnpj(
            @PathVariable String cnpj,
            @RequestParam Integer tenant) {
        GetAdminMapper admin = adminService.getAdminByCnpj(cnpj, tenant);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }


    @GetMapping()
    public ResponseEntity<Page<GetAdminMapper>> getAllAdmins(Pageable pageable) {
        var listAdmins = adminService.getAllAdmins(pageable);
        return new ResponseEntity<>(listAdmins, HttpStatus.OK);
    }

    @GetMapping(value = {"/{cnpj}/professors"}, params = {"tenant"})
    public ResponseEntity<List<ProfessorGet>> getAllProfessorsByCnpj(
            @PathVariable String cnpj, @RequestParam int tenant) {
        List<ProfessorGet> allProfessorsByCnpj = adminService.getAllProfessorsByCnpj(cnpj, tenant);
        return new ResponseEntity<>(allProfessorsByCnpj, HttpStatus.OK);
    }


    @PatchMapping(value = "update-first-name/{cnpj}", params = {"tenant"})
    public ResponseEntity<GetAdminMapper> updateFirstUsername(
            @PathVariable String cnpj,
            @RequestBody UpdateAdminFirstName updateAdminFirstName,
            @RequestParam Integer tenant) throws RequestExceptions {
        GetAdminMapper admin = adminService.updateAdminFirstName(cnpj, updateAdminFirstName, tenant);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @PatchMapping(value = "update-second-name/{cnpj}", params = {"tenant"})
    public ResponseEntity<GetAdminMapper> updateSecondUsername(
            @PathVariable String cnpj,
            @RequestBody UpdateAdminSecondName updateAdminSecondName,
            @RequestParam Integer tenant) throws RequestExceptions {
        GetAdminMapper admin = adminService.updateAdminSecondName(cnpj, updateAdminSecondName, tenant);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @PatchMapping(value = "update-password/{cnpj}", params = {"tenant"})
    public ResponseEntity<GetAdminMapper> updatePassword(
            @PathVariable String cnpj,
            @RequestBody UpdateAdminPassword updateAdminPassword,
            @RequestParam Integer tenant) throws RequestExceptions {
        GetAdminMapper admin = adminService.updateAdminPassword(cnpj, updateAdminPassword, tenant);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{cnpj}", params = {"tenant"})
    public ResponseEntity<String> deleteAdminByCnpj(@PathVariable String cnpj, @RequestParam Integer tenant) {
        String response = adminService.deleteAdminByCnpj(cnpj, tenant);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
