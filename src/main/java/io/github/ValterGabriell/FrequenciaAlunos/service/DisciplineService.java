package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.domain.discipline.Discipline;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.DisciplineRepository;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.discipline.CreateDiscipline;
import io.github.ValterGabriell.FrequenciaAlunos.util.GenerateSKId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplineService {
    private final DisciplineRepository disciplineRepository;

    public DisciplineService(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }


    public String insert(CreateDiscipline createDiscipline, int tenant) {
        //todo: só pode por disciplina pra professor existente
        //todo: por disciplina por cada local tenant
        //verificar nome e tenant


        List<Discipline> disciplines = disciplineRepository.findAll();
        if (!disciplines.isEmpty()) {
            boolean disciplinePresent
                    = disciplineRepository.findDisciplineByName(createDiscipline.getName()).isPresent();
            if (disciplinePresent) {
                throw new RequestExceptions("Disciplina com mesmo nome já cadastrada!");
            }
        }

        Discipline discipline = createDiscipline.toDiscipline();
        discipline.setSkid("-");
        discipline.setTenant(tenant);
        Discipline saved = disciplineRepository.save(discipline);
        saved.setSkid(GenerateSKId.generateSkId());
        Discipline updated = disciplineRepository.save(saved);
        return "SKID: " + updated.getSkid();
    }


    public String update(Discipline discipline, String skid, int tenant) {
        Discipline disciplinePresent
                = disciplineRepository.findDisciplineBySkidAndTenant(skid, tenant).orElseThrow(() ->
                new RequestExceptions("Disciplina não encontrada"));

        disciplinePresent.setName(disciplinePresent.getName());
        disciplinePresent.setDescription(disciplinePresent.getDescription());
        disciplinePresent.setProfessorId(disciplinePresent.getProfessorId());
        disciplinePresent.setSkid(disciplinePresent.getSkid());
        disciplinePresent.setDisciplineId(discipline.getDisciplineId());
        disciplinePresent.setTenant(disciplinePresent.getTenant());

        Discipline saved = disciplineRepository.save(disciplinePresent);
        return "SKID: " + saved.getSkid();
    }


    public List<Discipline> getAll(int tenant) {
        return disciplineRepository.getAllByTenant(tenant);
    }


    public Discipline getById(String skid, int tenant) {
        return disciplineRepository.findDisciplineBySkidAndTenant(skid, tenant).orElseThrow(() ->
                new RequestExceptions("Disciplina não encontrada"));
    }


    public void delete(String skid, int tenant) {
        Discipline disciplinePresent
                = disciplineRepository.findDisciplineBySkidAndTenant(skid, tenant).orElseThrow(() ->
                new RequestExceptions("Disciplina não encontrada"));
        disciplineRepository.delete(disciplinePresent);
    }
}
