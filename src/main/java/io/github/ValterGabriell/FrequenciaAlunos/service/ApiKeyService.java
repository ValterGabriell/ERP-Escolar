package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.domain.ApiKeyEntity;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.ApiKeyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApiKeyService {
    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    public ApiKeyEntity getApiKey(String client) {
        Optional<ApiKeyEntity> apiKey = apiKeyRepository.findByTenant(client);
        return apiKey.get();
    }
}
