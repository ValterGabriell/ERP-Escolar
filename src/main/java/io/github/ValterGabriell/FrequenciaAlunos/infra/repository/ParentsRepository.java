package io.github.ValterGabriell.FrequenciaAlunos.infra.repository;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface ParentsRepository extends JpaRepository<Parent, UUID> {

    public Optional<Parent> findByIdentifierNumberAndTenant(String identifierNumber,int tenant);
    Optional<Parent> findBySkidAndTenant(String skid,int tenant);

}
