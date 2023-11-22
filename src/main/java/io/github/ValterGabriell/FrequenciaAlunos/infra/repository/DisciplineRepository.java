package io.github.ValterGabriell.FrequenciaAlunos.infra.repository;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DisciplineRepository extends JpaRepository<Discipline, UUID> {
    Optional<Discipline> findDisciplineByNameAndTenant(String name, int tenant);
    Optional<Discipline> findDisciplineBySkidAndTenant(String skid, int tenant);
    List<Discipline> getAllByTenant(int tenant);
}
