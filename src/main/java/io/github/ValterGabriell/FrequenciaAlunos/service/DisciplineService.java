package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.controller.DisciplineController;
import io.github.ValterGabriell.FrequenciaAlunos.domain.discipline.Discipline;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.DisciplineRepository;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.PatternResponse;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.discipline.CreateDiscipline;
import io.github.ValterGabriell.FrequenciaAlunos.util.GenerateSKId;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class DisciplineService {
    private final DisciplineRepository disciplineRepository;

    public DisciplineService(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }


    public PatternResponse<String> insert(CreateDiscipline createDiscipline, int tenant) {
        //todo: só pode por disciplina pra professor existente
        //todo: por disciplina por cada local tenant
        //verificar nome e tenant


        List<Discipline> disciplines = disciplineRepository.findAll();
        if (!disciplines.isEmpty()) {
            boolean disciplinePresent
                    = disciplineRepository.findDisciplineByNameAndTenant(createDiscipline.getName(), tenant).isPresent();
            if (disciplinePresent) {
                throw new RequestExceptions("Disciplina com mesmo nome já cadastrada!");
            }
        }

        Discipline discipline = createDiscipline.toDiscipline();
        discipline.setTenant(tenant);
        discipline.setSkid(GenerateSKId.generateSkId());
        Discipline saved = disciplineRepository.save(discipline);
        saved.add(linkTo(methodOn(DisciplineController.class).getById(saved.getSkid(), tenant)).withSelfRel());

        return new PatternResponse<>(
                saved.getSkid(),
                saved.getLinks()
                );
    }


    public String update(Discipline discipline, String skid, int tenant) {
        Discipline disciplinePresent
                = disciplineRepository.findDisciplineBySkidAndTenant(skid, tenant).orElseThrow(() ->
                new RequestExceptions("Disciplina não encontrada"));

        disciplinePresent.setName(discipline.getName());
        disciplinePresent.setDescription(discipline.getDescription());
        disciplinePresent.setProfessorId(discipline.getProfessorId());

        disciplinePresent.setAdminId(disciplinePresent.getAdminId());
        disciplinePresent.setSkid(disciplinePresent.getSkid());
        disciplinePresent.setTenant(disciplinePresent.getTenant());
        disciplinePresent.setDisciplineId(disciplinePresent.getDisciplineId());

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
