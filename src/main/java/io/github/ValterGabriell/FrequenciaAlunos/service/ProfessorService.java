package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.controller.ProfessorController;
import io.github.ValterGabriell.FrequenciaAlunos.domain.login.Login;
import io.github.ValterGabriell.FrequenciaAlunos.domain.parents.Parent;
import io.github.ValterGabriell.FrequenciaAlunos.domain.professors.Professor;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.LoginRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.ProfessorRepository;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.professor.CreateProfessor;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.professor.ProfessorGet;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.professor.UpdateProfessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProfessorService {
    private final ProfessorRepository professorRepository;
    private final LoginRepository loginRepository;

    public ProfessorService(ProfessorRepository professorRepository, LoginRepository loginRepository) {
        this.professorRepository = professorRepository;
        this.loginRepository = loginRepository;
    }
    private Login createLogin(Professor professor, int tenant) {
        // Cria um objeto 'Login' para o fluxo de login
        Login login = new Login(
                professor.getContacts().get(0).getEmail(),
                professor.getSkid(),
                tenant
        );

        // Salva o objeto 'Login' no repositório
        Login loginSaved = loginRepository.save(login);

        // Define o campo 'skid' do 'Login' com o ID gerado após a primeira inserção
        loginSaved.setSkid(loginSaved.getId());

        // Salva o objeto 'Login' atualizado com o 'skid' no repositório novamente
        return loginRepository.save(loginSaved);
    }

    public String createProfessor(CreateProfessor createProfessor, int tenant) {
        Professor professor = createProfessor.toProfessor();
        professor.setTenant(tenant);
        professor.setSkid(UUID.randomUUID().toString());
        Professor professorSaved = professorRepository.save(professor);
        createLogin(professorSaved,tenant);
        return professorSaved.getFirstName();
    }

    public String updateProfessor(UpdateProfessor updateProfessor,
                                  int tenant,
                                  String skid) {
        Professor professor = professorRepository.findBySkidAndTenant(skid, tenant).orElseThrow(() -> {
            throw new RequestExceptions("Professor não encontrado! -> " + skid);
        });
        professor.setContacts(updateProfessor.getContacts());
        professor.setFirstName(updateProfessor.getFirstName());
        professor.setLastName(updateProfessor.getLastName());
        professor.setAverage(updateProfessor.getAverage());
        professor.setSchoolClasses(updateProfessor.getSchoolClasses());
        professor.setContacts(updateProfessor.getContacts());
        professor.setFinishedDate(updateProfessor.getFinishedDate());
        professor.setStartDate(updateProfessor.getStartDate());

        Professor professorUpdated = professorRepository.save(professor);

        return professorUpdated.getFirstName();
    }

    public ProfessorGet getBySkId(int tenant, String skId) {
        Professor professor = professorRepository.findBySkidAndTenant(skId, tenant).orElseThrow(() -> {
            throw new RequestExceptions("Professor não encontrado! -> " + skId);
        });

        return professor.toGetProfessor();
    }

    public Page<ProfessorGet> getAllProfessors(int tenant, Pageable pageable) {
        Page<Professor> professorList = professorRepository.findAll(pageable);
        List<Professor> listaComTenantFiltrado =
                professorList.stream()
                        .filter(professor -> professor.getTenant() == tenant)
                        .toList();
        List<ProfessorGet> collect =
                listaComTenantFiltrado
                        .stream()
                        .map(professor -> professor
                                .add(linkTo(methodOn(ProfessorController.class)
                                        .getByIdentifier(professor.getSkid(), professor.getTenant()))
                                        .withSelfRel())
                                .toGetProfessor()
                        )
                        .toList();

        return new PageImpl<>(collect);
    }

    public void deleteProfessorByIdentifierNumber(int tenant, String skid) {
        Professor professor = professorRepository.findBySkidAndTenant(skid, tenant).orElseThrow(() -> {
            throw new RequestExceptions("Professor não encontrado! -> " + skid);
        });
        professorRepository.delete(professor);
    }
}
