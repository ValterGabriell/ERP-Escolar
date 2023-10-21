package io.github.ValterGabriell.FrequenciaAlunos.infra.repository;

import io.github.ValterGabriell.FrequenciaAlunos.domain.students.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentsRepository extends JpaRepository<Student, String> {

    @Query(value = "SELECT * FROM tbl_estudantes WHERE cpf = :cpf AND tenant = :tenant", nativeQuery = true)
    Optional<Student> findById(@Param("cpf") String cpf, @Param("tenant") int tenant);
}
