package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.domain.discipline.Discipline;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.DisciplineRepository;
import io.github.ValterGabriell.FrequenciaAlunos.util.GenerateSKId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplineService {
    private final DisciplineRepository disciplineRepository;

    public DisciplineService(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }



    public String insert(Discipline discipline) {
        //todo: só pode por disciplina pra professor existente
        //todo: por disciplina por cada local tenant
        //verificar nome e tenant


        List<Discipline> disciplines = disciplineRepository.findAll();
        if (!disciplines.isEmpty()){
            boolean disciplinePresent
                    = disciplineRepository.findDisciplineByName(discipline.getName()).isPresent();
            if (disciplinePresent) {
                throw new RequestExceptions("Disciplina com mesmo nome já cadastrada!");
            }
        }

        discipline.setSkid("-");
        Discipline saved = disciplineRepository.save(discipline);
        saved.setSkid(GenerateSKId.generateSkId(saved.getId()));
        Discipline updated = disciplineRepository.save(saved);
        return updated.getSkid();
    }


    public String update(Discipline discipline, String id, int tenant) {
        Discipline disciplinePresent
                = disciplineRepository.findDisciplineByIdAndTenant(id, tenant).orElseThrow(() ->
                new RequestExceptions("Disciplina não encontrada"));

        disciplinePresent.setName(disciplinePresent.getName());
        disciplinePresent.setDescription(disciplinePresent.getDescription());
        disciplinePresent.setProfessorId(disciplinePresent.getProfessorId());
        disciplinePresent.setSkid(disciplinePresent.getSkid());
        disciplinePresent.setId(discipline.getId());
        disciplinePresent.setTenant(disciplinePresent.getTenant());

        Discipline saved = disciplineRepository.save(disciplinePresent);
        return saved.getSkid();
    }


    public List<Discipline> getAll(int tenant) {
        return disciplineRepository.getAllByTenant(tenant);
    }


    public Discipline getById(String id, int tenant) {
        Discipline disciplinePresent
                = disciplineRepository.findDisciplineByIdAndTenant(id, tenant).orElseThrow(() ->
                new RequestExceptions("Disciplina não encontrada"));
        return disciplinePresent;
    }


    public void delete(String id, int tenant) {
        Discipline disciplinePresent
                = disciplineRepository.findDisciplineByIdAndTenant(id, tenant).orElseThrow(() ->
                new RequestExceptions("Disciplina não encontrada"));
        disciplineRepository.delete(disciplinePresent);
    }
}
