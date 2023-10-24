package io.github.ValterGabriell.FrequenciaAlunos.infra.repository;

import io.github.ValterGabriell.FrequenciaAlunos.domain.discipline.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DisciplineRepository extends JpaRepository<Discipline, String> {
    Optional<Discipline> findDisciplineByName(String name);
    Optional<Discipline> findDisciplineBySkidAndTenant(String skid, int tenant);
    List<Discipline> getAllByTenant(int tenant);
}
