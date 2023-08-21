package io.github.ValterGabriell.FrequenciaAlunos.domain.admins;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    }
