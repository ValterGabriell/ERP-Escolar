package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.domain.professors.Professor;
import io.github.ValterGabriell.FrequenciaAlunos.domain.school_class.SchoolClass;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.ProfessorRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.SchoolClassesRepository;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.schoolclass.CreateSC;
import io.github.ValterGabriell.FrequenciaAlunos.util.GenerateSKId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchoolClassService {
    private final SchoolClassesRepository schoolClassesRepository;
    private final ProfessorRepository professorRepository;

    public SchoolClassService(SchoolClassesRepository schoolClassesRepository, ProfessorRepository professorRepository) {
        this.schoolClassesRepository = schoolClassesRepository;
        this.professorRepository = professorRepository;
    }

    public String create(
            CreateSC createSC,
            String adminId,
            int tenant) {

        SchoolClass sc = createSC.toSC();

        List<Professor> professorsBySkId =
                professorRepository.findAllBySkidInAndTenant(createSC.getProfessors(), tenant);
        List<Professor> professors = new ArrayList<>(professorsBySkId);

        sc.setProfessors(professors);
        sc.setAdminId(adminId);
        sc.setTenant(tenant);
        sc.setSkid(GenerateSKId.generateSkId());

        SchoolClass saved = schoolClassesRepository.save(sc);
        return "SKID: " + saved.getSkid();
    }

    public SchoolClass getBySkId(String skid, int tenant) {
        return schoolClassesRepository.findBySkidAndTenant(
                skid,
                tenant
        ).orElseThrow(() -> new RequestExceptions("Sala não cadastrada!"));
    }

    public List<SchoolClass> getAll(int tenant) {
        return schoolClassesRepository.findAllByTenant(
                tenant
        );
    }

    public void delete(String skid, int tenant) {
        SchoolClass schoolClass = schoolClassesRepository.findBySkidAndTenant(
                skid,
                tenant
        ).orElseThrow(() -> new RequestExceptions("Sala não cadastrada!"));
        schoolClassesRepository.delete(schoolClass);
    }

}
