package io.github.ValterGabriell.FrequenciaAlunos.ANovo.controller;


import io.github.ValterGabriell.FrequenciaAlunos.ANovo.ports.admin.IServiceAdminPort;
import io.github.ValterGabriell.FrequenciaAlunos.dto.admin.DtoCreateNewAdmin;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/")
public class AdminController {

    private final IServiceAdminPort iServiceAdminPort;

    public AdminController(IServiceAdminPort iServiceAdminPort) {
        this.iServiceAdminPort = iServiceAdminPort;
    }

    @PostMapping
    public ResponseEntity<String> createAdmin(@RequestBody DtoCreateNewAdmin dtoCreateNewAdmin) {
        String adminId = iServiceAdminPort.create(dtoCreateNewAdmin);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(adminId);
    }


}
