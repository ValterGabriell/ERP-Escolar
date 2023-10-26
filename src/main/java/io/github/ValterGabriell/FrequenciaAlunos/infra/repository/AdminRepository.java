package io.github.ValterGabriell.FrequenciaAlunos.infra.repository;

import io.github.ValterGabriell.FrequenciaAlunos.domain.admins.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;


public interface AdminRepository extends JpaRepository<Admin, String> {

    @Query(value = "SELECT * FROM tbl_admin WHERE email = :email AND tenant =:tenant", nativeQuery = true)
    Optional<Admin> findByEmail(@Param("email") String email, @Param("tenant") Integer tenant);

    @Query(value = "SELECT * FROM tbl_admin WHERE cnpj = :cnpj AND tenant =:tenant", nativeQuery = true)
    Optional<Admin> findByCnpj(@Param("cnpj")String cnpj, @Param("tenant") Integer tenant);

    @Query(value = "SELECT * FROM tbl_admin WHERE skId = :skId AND tenant =:tenant", nativeQuery = true)
    Optional<Admin> findBySkid(@Param("skId")String skId, @Param("tenant") Integer tenant);

    Optional<Admin> findByTenant(int tenantId);

}
