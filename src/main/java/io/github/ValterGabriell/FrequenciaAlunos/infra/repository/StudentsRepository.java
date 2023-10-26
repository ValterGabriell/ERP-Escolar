package io.github.ValterGabriell.FrequenciaAlunos.infra.repository;

import io.github.ValterGabriell.FrequenciaAlunos.domain.students.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentsRepository extends JpaRepository<Student, String> {

    @Query(value = "SELECT * FROM tbl_estudantes WHERE skid = :skid AND tenant = :tenant", nativeQuery = true)
    Optional<Student> findBySkId(@Param("skid") String skid, @Param("tenant") int tenant);

    @Query(value = "SELECT * FROM tbl_estudantes WHERE studentId = :studentId AND tenant = :tenant", nativeQuery = true)
    Optional<Student> findById(@Param("studentId") String studentId, @Param("tenant") int tenant);


    List<Student> findAllByTenant(int tenant);

}
