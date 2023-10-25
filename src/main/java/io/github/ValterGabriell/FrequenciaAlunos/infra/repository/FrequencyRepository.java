package io.github.ValterGabriell.FrequenciaAlunos.infra.repository;

import io.github.ValterGabriell.FrequenciaAlunos.domain.frequency.Frequency;
import io.github.ValterGabriell.FrequenciaAlunos.domain.students.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FrequencyRepository extends JpaRepository<Frequency, String> {
    Frequency findByFrequencyIdAndTenant(String frequencyId, int tenant);
}
