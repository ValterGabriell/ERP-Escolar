package io.github.ValterGabriell.FrequenciaAlunos.infra.repository;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Salas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SalasRepository extends JpaRepository<Salas, UUID> {
    Optional<Salas> findBySkidAndTenant(String skid, int tenant);

    List<Salas> findAllByTenant(int tenant);

}
