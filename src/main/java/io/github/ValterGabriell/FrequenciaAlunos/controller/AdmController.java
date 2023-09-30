package io.github.ValterGabriell.FrequenciaAlunos.controller;


import io.github.ValterGabriell.FrequenciaAlunos.excpetion.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.admin.CreateNewAdmin;
import io.github.ValterGabriell.FrequenciaAlunos.service.AdmService;
import io.github.ValterGabriell.FrequenciaAlunos.domain.admins.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.admin.GetAdmin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("admin")
public class AdmController {
    private final AdmService adminService;

    public AdmController(AdmService adminService) {
        this.adminService = adminService;
    }

    @PostMapping()
    public ResponseEntity<String> insertAdmin(@RequestBody CreateNewAdmin insertAdmin) {
        var newAdmin = adminService.createNewAdmin(insertAdmin);
        return new ResponseEntity<>(newAdmin, HttpStatus.CREATED);
    }

    @GetMapping(params = "adminId")
    public ResponseEntity<GetAdmin> getAdminById(@RequestParam  String adminId){
        GetAdmin admin = adminService.getAdminById(UUID.fromString(adminId));
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }


    @GetMapping()
    public ResponseEntity<List<GetAdmin>> getAllAdmins() {
        var listAdmins = adminService.getAllAdmins();
        return new ResponseEntity<>(listAdmins, HttpStatus.OK);
    }

    @PutMapping(value = "update-username", params = {"adminId", "newUsername"})
    public ResponseEntity<GetAdmin> updateUsername(@RequestParam String adminId, @RequestParam String newUsername) throws RequestExceptions {
        GetAdmin admin = adminService.updateAdminUsername(UUID.fromString(adminId), newUsername);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @PutMapping(value = "update-password", params = {"adminId", "newPass"})
    public ResponseEntity<GetAdmin> updatePassword(@RequestParam String adminId, @RequestParam String newPass) throws RequestExceptions {
        GetAdmin admin = adminService.updateAdminPassword(UUID.fromString(adminId), newPass);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @DeleteMapping(params = "adminId")
    public ResponseEntity<String> deleteAdminById(@RequestParam String adminId){
        String response = adminService.deleteAdminById(UUID.fromString(adminId));
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);

    }

}
