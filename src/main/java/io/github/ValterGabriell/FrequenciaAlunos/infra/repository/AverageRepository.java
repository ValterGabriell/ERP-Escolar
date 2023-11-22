package io.github.ValterGabriell.FrequenciaAlunos.infra.repository;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Average;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AverageRepository extends JpaRepository<Average, UUID> {
    Optional<Average> findByStudentSkIdAndDisciplineSkIdAndEvaluationAndTenant(
            String studentSkId, String disciplineSkId, int evaluation, int tenant);

    List<Average> findAllByStudentSkIdAndTenant(String studentSkId, int tenant);
}
