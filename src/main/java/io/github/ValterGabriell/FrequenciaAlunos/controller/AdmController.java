package io.github.ValterGabriell.FrequenciaAlunos.controller;


import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.admin.*;
import io.github.ValterGabriell.FrequenciaAlunos.service.AdmService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/v1/admin")
public class AdmController {
    private final AdmService adminService;

    public AdmController(AdmService adminService) {
        this.adminService = adminService;
    }

    @PostMapping(params = {"tenantId"})
    public ResponseEntity<String> insertAdmin(@RequestBody CreateNewAdmin insertAdmin, @RequestParam Integer tenantId) {
        var newAdmin = adminService.createNewAdmin(insertAdmin, tenantId);
        return new ResponseEntity<>(newAdmin, HttpStatus.CREATED);
    }

    @GetMapping(value = {"/{cnpj}"}, params = {"tenantId"})
    public ResponseEntity<GetAdminMapper> getAdminBySkId(
            @PathVariable String cnpj,
            @RequestParam Integer tenantId) {
        GetAdminMapper admin = adminService.getAdminByCnpj(cnpj, tenantId);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }


    @GetMapping()
    public ResponseEntity<Page<GetAdminMapper>> getAllAdmins(Pageable pageable) {
        var listAdmins = adminService.getAllAdmins(pageable);
        return new ResponseEntity<>(listAdmins, HttpStatus.OK);
    }

    @PutMapping(value = "update-first-name/{cnpj}", params = {"tenantId"})
    public ResponseEntity<GetAdminMapper> updateFirstUsername(
            @PathVariable String cnpj,
            @RequestBody UpdateAdminFirstName updateAdminFirstName,
            @RequestParam Integer tenantId) throws RequestExceptions {
        GetAdminMapper admin = adminService.updateAdminFirstName(cnpj, updateAdminFirstName, tenantId);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @PutMapping(value = "update-second-name/{cnpj}", params = {"tenantId"})
    public ResponseEntity<GetAdminMapper> updateSecondUsername(
            @PathVariable String cnpj,
            @RequestBody UpdateAdminSecondName updateAdminSecondName,
            @RequestParam Integer tenantId) throws RequestExceptions {
        GetAdminMapper admin = adminService.updateAdminSecondName(cnpj, updateAdminSecondName, tenantId);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @PutMapping(value = "update-password/{cnpj}", params = {"adminId", "tenantId"})
    public ResponseEntity<GetAdminMapper> updatePassword(
            @PathVariable String cnpj,
            @RequestBody UpdateAdminPassword updateAdminPassword,
            @RequestParam Integer tenantId) throws RequestExceptions {
        GetAdminMapper admin = adminService.updateAdminPassword(cnpj, updateAdminPassword, tenantId);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @DeleteMapping(params = {"adminId", "tenantId"})
    public ResponseEntity<String> deleteAdminByCnpj(@RequestParam String adminId, @RequestParam Integer tenantId) {
        String response = adminService.deleteAdminById(adminId, tenantId);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
