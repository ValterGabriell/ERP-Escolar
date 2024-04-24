package io.github.ValterGabriell.FrequenciaAlunos.ANovo.infra.repository.admin;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository_V2 extends JpaRepository<Admin, String> {

    Optional<Admin> findByCnpjAndTenant(@Param("cnpj")String cnpj, @Param("tenant") Integer tenant);

    Optional<Admin> findBySkidAndTenant(@Param("skId")String skId, @Param("tenant") Integer tenant);

    Optional<Admin> findByCnpj(@Param("cnpj")String cnpj);

    Optional<Admin> findByTenant(int tenantId);

    Optional<Admin> findByCnpjAndPasswordAndTenant(String cnpj, String password, Integer tenant);

    Admin findByQualquerCoisa(String string);
}
