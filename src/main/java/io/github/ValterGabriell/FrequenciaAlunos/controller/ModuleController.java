package io.github.ValterGabriell.FrequenciaAlunos.controller;

import io.github.ValterGabriell.FrequenciaAlunos.domain.ModulesEntity;
import io.github.ValterGabriell.FrequenciaAlunos.dto.admin.ModulesDTO;
import io.github.ValterGabriell.FrequenciaAlunos.service.ModuleService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.util.List;

@RestController
@RequestMapping("/api/v1/module")
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @PostMapping(value = "insert", params = {"tenant"})
    public ResponseEntity<String> insertModules(@RequestBody ModulesDTO modulesDTO, @RequestParam Integer tenant) {
        moduleService.insertModules(modulesDTO, tenant);
        return ResponseEntity.ok().body("Módulos adicionados!");
    }

    @GetMapping(value = "get", params = {"tenant"})
    public ResponseEntity<ModulesEntity> getModules(@RequestParam Integer tenant) {
        ModulesEntity modules = moduleService.getModules(tenant);
        return ResponseEntity.ok().body(modules);
    }

    @PatchMapping(value = "update", params = {"tenant"})
    public ResponseEntity<String> updateModules(@RequestBody ModulesDTO modulesDTO, @RequestParam Integer tenant) {
        moduleService.updateModules(modulesDTO, tenant);
        return ResponseEntity.ok().body("Módulos atualizados!");
    }

    @DeleteMapping(value = "delete", params = {"tenant"})
    public ResponseEntity<String> deleteModules(@RequestParam Integer tenant) {
        moduleService.deleteModules(tenant);
        return ResponseEntity.ok().body("Módulos deletados!");
    }
}
