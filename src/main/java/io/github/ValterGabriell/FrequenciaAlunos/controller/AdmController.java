package io.github.ValterGabriell.FrequenciaAlunos.controller;


import io.github.ValterGabriell.FrequenciaAlunos.service.AdmService;
import io.github.ValterGabriell.FrequenciaAlunos.domain.admins.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.domain.admins.dto.GetAdmin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("admin")
public class AdmController {
    private final AdmService adminService;

    public AdmController(AdmService adminService) {
        this.adminService = adminService;
    }

    @PostMapping()
    public ResponseEntity<String> insertAdmin(@RequestBody Admin insertAdmin) {
        var newAdmin = adminService.createNewAdmin(insertAdmin);
        return new ResponseEntity<String>(newAdmin, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<GetAdmin>> getAllAdmins() {
        var listAdmins = adminService.getAllAdmins();
        return new ResponseEntity<List<GetAdmin>>(listAdmins, HttpStatus.OK);
    }
}
