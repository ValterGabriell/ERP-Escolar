package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.domain.ModulesEntity;
import io.github.ValterGabriell.FrequenciaAlunos.dto.admin.ModulesDTO;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.ModuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModuleService {
    private final ModuleRepository moduleRepository;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    private Optional<ModulesEntity> moduleClientExists(String client) {
        return moduleRepository.findByTenant(client);
    }

    public void insertModules(ModulesDTO modulesDTO, Integer client) {
        ModulesEntity modulesEntity = new ModulesEntity(client.toString(), modulesDTO.getModules());
        moduleRepository.save(modulesEntity);
    }

    public void insertModules(ModulesEntity modulesEntity) {
        moduleRepository.save(modulesEntity);
    }

    public ModulesEntity getModules(Integer client) {
        var modulesEntity = moduleClientExists(client.toString());
        if (!modulesEntity.isPresent())
            throw new RequestExceptions("Módulos do tenant especificado não encontrado!");
        return modulesEntity.get();
    }

    public void updateModules(ModulesDTO modulesDTO, Integer client) {
        if (!moduleClientExists(client.toString()).isPresent())
            throw new RequestExceptions("Módulos do tenant especificado não encontrado!");
        ModulesEntity modulesEntity = moduleRepository.findByTenant(client.toString()).get();
        modulesEntity.setModules(modulesDTO.getModules());
        moduleRepository.save(modulesEntity);
    }

    public void deleteModules(Integer client) {
        if (!moduleClientExists(client.toString()).isPresent())
            throw new RequestExceptions("Módulos do tenant especificado não encontrado!");
        ModulesEntity modulesEntity = moduleRepository.findByTenant(client.toString()).get();
        moduleRepository.delete(modulesEntity);
    }
}
