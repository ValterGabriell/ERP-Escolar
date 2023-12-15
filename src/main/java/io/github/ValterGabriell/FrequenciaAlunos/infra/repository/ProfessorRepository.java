package io.github.ValterGabriell.FrequenciaAlunos.infra.repository;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ProfessorRepository extends JpaRepository<Professor, UUID> {
    Optional<Professor> findBySkidAndTenant(String skid, int tenant);
    List<Professor> findAllBySkidInAndTenant(List<String> skid, int tenant);
}
