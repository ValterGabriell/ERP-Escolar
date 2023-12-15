package io.github.ValterGabriell.FrequenciaAlunos.infra.repository;

import io.github.ValterGabriell.FrequenciaAlunos.domain.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SchoolClassesRepository extends JpaRepository<SchoolClass, UUID> {
    Optional<SchoolClass> findByNameAndPeriodAndTenant(String name, String period, int tenant);

    Optional<SchoolClass> findBySkidAndTenant(String skid, int tenant);

    List<SchoolClass> findAllByTenant(int tenant);

}
