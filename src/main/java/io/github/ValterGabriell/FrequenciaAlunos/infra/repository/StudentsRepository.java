package io.github.ValterGabriell.FrequenciaAlunos.infra.repository;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentsRepository extends JpaRepository<Student, String> {

    Optional<Student> findBySkidAndTenant(@Param("skid") String skid, @Param("tenant") int tenant);

    Optional<Student> studentIdAndTenant(@Param("studentId") String studentId, @Param("tenant") int tenant);


    List<Student> findAllByTenant(int tenant);
    Page<Student> findAllByTenant(Pageable pageable, int tenant);

}
