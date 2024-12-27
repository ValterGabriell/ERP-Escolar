package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.controller.DisciplineController;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Disciplina;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.DisciplineRepository;
import io.github.ValterGabriell.FrequenciaAlunos.dto.PatternResponse;
import io.github.ValterGabriell.FrequenciaAlunos.dto.discipline.CreateDiscipline;
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
        List<Disciplina> disciplinas = disciplineRepository.findAll();
        if (!disciplinas.isEmpty()) {
            boolean disciplinePresent
                    = disciplineRepository.findDisciplineByNameAndTenant(createDiscipline.getName(), tenant).isPresent();
            if (disciplinePresent) {
                throw new RequestExceptions("Disciplina com mesmo nome já cadastrada!");
            }
        }

        Disciplina disciplina = createDiscipline.toDiscipline();
        disciplina.setTenant(tenant);
        disciplina.setSkid(GenerateSKId.generateSkId());
        Disciplina saved = disciplineRepository.save(disciplina);
        saved.add(linkTo(methodOn(DisciplineController.class).getById(saved.getSkid(), tenant)).withSelfRel());

        return new PatternResponse<>(
                saved.getSkid(),
                saved.getLinks()
                );
    }


    public String update(Disciplina disciplina, String skid, int tenant) {
        Disciplina disciplinaPresent
                = disciplineRepository.findDisciplineBySkidAndTenant(skid, tenant).orElseThrow(() ->
                new RequestExceptions("Disciplina não encontrada"));

        disciplinaPresent.setName(disciplina.getName());
        disciplinaPresent.setDescription(disciplina.getDescription());
        disciplinaPresent.setProfessorId(disciplina.getProfessorId());

        disciplinaPresent.setSalaId(disciplinaPresent.getSalaId());
        disciplinaPresent.setSkid(disciplinaPresent.getSkid());
        disciplinaPresent.setTenant(disciplinaPresent.getTenant());
        disciplinaPresent.setDisciplineId(disciplinaPresent.getDisciplineId());

        Disciplina saved = disciplineRepository.save(disciplinaPresent);
        return "SKID: " + saved.getSkid();
    }


    public List<Disciplina> getAll(int tenant) {
        return disciplineRepository.getAllByTenant(tenant);
    }


    public Disciplina getById(String skid, int tenant) {
        return disciplineRepository.findDisciplineBySkidAndTenant(skid, tenant).orElseThrow(() ->
                new RequestExceptions("Disciplina não encontrada"));
    }


    public void delete(String skid, int tenant) {
        Disciplina disciplinaPresent
                = disciplineRepository.findDisciplineBySkidAndTenant(skid, tenant).orElseThrow(() ->
                new RequestExceptions("Disciplina não encontrada"));
        disciplineRepository.delete(disciplinaPresent);
    }
}
