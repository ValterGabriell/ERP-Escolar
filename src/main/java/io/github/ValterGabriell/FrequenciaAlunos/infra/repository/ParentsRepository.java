package io.github.ValterGabriell.FrequenciaAlunos.infra.repository;

import io.github.ValterGabriell.FrequenciaAlunos.domain.parents.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface ParentsRepository extends JpaRepository<Parent, String> {
    @Query(value = "SELECT * FROM tbl_pais WHERE identifierNumber = :identifierNumber AND tenant = :tenant",
            nativeQuery = true)
    public Optional<Parent> findByIdentifierNumber(@Param("identifierNumber") String identifierNumber, @Param("tenant") int tenant);

}
