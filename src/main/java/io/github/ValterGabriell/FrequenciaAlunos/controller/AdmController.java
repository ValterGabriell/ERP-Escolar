package io.github.ValterGabriell.FrequenciaAlunos.controller;


import io.github.ValterGabriell.FrequenciaAlunos.dto.admin.*;
import io.github.ValterGabriell.FrequenciaAlunos.dto.professor.ProfessorGet;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.service.AdmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class AdmController {
    private final AdmService adminService;

    public AdmController(AdmService adminService) {
        this.adminService = adminService;
    }

    @PostMapping(value = "/api/v1/admin/insert")
    public ResponseEntity<String> insertAdmin(@RequestBody CreateNewAdmin insertAdmin) {
        var newAdmin = adminService.createNewAdmin(insertAdmin);
        return new ResponseEntity<>(newAdmin, HttpStatus.CREATED);
    }

    @PostMapping(value = {"/api/v1/admin/login"})
    public ResponseEntity<LoginResponse> loginUser(
            @RequestBody LoginDTO loginDTO) {
        var loginResponse = adminService.loginAdmin(loginDTO);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = {"/v1/logout"}, params = {"tenant"})
    public ResponseEntity<String> logoutUser(
            @RequestParam Integer tenant) {
        adminService.logoutUser(tenant);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = {"/v1/{cnpj}"}, params = {"tenant"})
    public ResponseEntity<GetAdminMapper> getAdminByCnpj(
            @PathVariable String cnpj,
            @RequestParam Integer tenant) {
        GetAdminMapper admin = adminService.getAdminByCnpj(cnpj, tenant);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @GetMapping(value = {"/v1/{cnpj}/professors"}, params = {"tenant"})
    public ResponseEntity<List<ProfessorGet>> getAllProfessorsByCnpj(
            @PathVariable String cnpj, @RequestParam int tenant) {
        List<ProfessorGet> allProfessorsByCnpj = adminService.getAllProfessorsByCnpj(cnpj, tenant);
        return new ResponseEntity<>(allProfessorsByCnpj, HttpStatus.OK);
    }


    @PatchMapping(value = "/v1/update-first-name/{cnpj}", params = {"tenant"})
    public ResponseEntity<GetAdminMapper> updateFirstUsername(
            @PathVariable String cnpj,
            @RequestBody UpdateAdminFirstName updateAdminFirstName,
            @RequestParam Integer tenant) throws RequestExceptions {
        GetAdminMapper admin = adminService.updateAdminFirstName(cnpj, updateAdminFirstName, tenant);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @PatchMapping(value = "/v1/update-second-name/{cnpj}", params = {"tenant"})
    public ResponseEntity<GetAdminMapper> updateSecondUsername(
            @PathVariable String cnpj,
            @RequestBody UpdateAdminSecondName updateAdminSecondName,
            @RequestParam Integer tenant) throws RequestExceptions {
        GetAdminMapper admin = adminService.updateAdminSecondName(cnpj, updateAdminSecondName, tenant);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @PatchMapping(value = "/v1/update-password/{cnpj}", params = {"tenant"})
    public ResponseEntity<GetAdminMapper> updatePassword(
            @PathVariable String cnpj,
            @RequestBody UpdateAdminPassword updateAdminPassword,
            @RequestParam Integer tenant) throws RequestExceptions {
        GetAdminMapper admin = adminService.updateAdminPassword(cnpj, updateAdminPassword, tenant);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @DeleteMapping(value = "/v1/{cnpj}", params = {"tenant"})
    public ResponseEntity<String> deleteAdminByCnpj(@PathVariable String cnpj, @RequestParam Integer tenant) {
        String response = adminService.deleteAdminByCnpj(cnpj, tenant);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
