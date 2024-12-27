package io.github.ValterGabriell.FrequenciaAlunos.infra.repository;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DisciplineRepository extends JpaRepository<Disciplina, UUID> {
    Optional<Disciplina> findDisciplineByNameAndTenant(String name, int tenant);
    Optional<Disciplina> findDisciplineBySkidAndTenant(String skid, int tenant);
    List<Disciplina> getAllByTenant(int tenant);
}
