package io.github.ValterGabriell.FrequenciaAlunos.infra.repository;

import io.github.ValterGabriell.FrequenciaAlunos.domain.parents.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface ParentsRepository extends JpaRepository<Parent, String> {

    public Optional<Parent> findByIdentifierNumberAndTenant(String identifierNumber,int tenant);
    Optional<Parent> findBySkidAndTenant(String skid,int tenant);

}
