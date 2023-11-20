package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Average;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.AverageRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.DisciplineRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.StudentsRepository;
import io.github.ValterGabriell.FrequenciaAlunos.dto.average.InsertAverage;
import io.github.ValterGabriell.FrequenciaAlunos.util.GenerateSKId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AverageService {
    private final AverageRepository averageRepository;
    private final StudentsRepository studentsRepository;
    private final DisciplineRepository disciplineRepository;

    public AverageService(AverageRepository averageRepository, StudentsRepository studentsRepository, DisciplineRepository disciplineRepository) {
        this.averageRepository = averageRepository;
        this.studentsRepository = studentsRepository;
        this.disciplineRepository = disciplineRepository;
    }

    @Transactional
    public String insert(InsertAverage insertAverage, int tenant) {
        boolean averagePresent =
                getByStudentSkIdAndDisciplineSkIdAndAverageAndEvaluationAndTenant(insertAverage, tenant).isPresent();

        if (averagePresent) throw new RequestExceptions("Nota já cadastrada!",
                "Do you wanna update? Check the documentation!");

        Average average = insertAverage.toAverage();
        average.setSkid(GenerateSKId.generateSkId());
        average.setTenant(tenant);
        average.setAverage(insertAverage.getAverage());

        Average saved = averageRepository.save(average);

        return "SKID: " + saved.getSkid();
    }

    private Optional<Average> getByStudentSkIdAndDisciplineSkIdAndAverageAndEvaluationAndTenant(InsertAverage insertAverage,
                                                                                                int tenant) {
        boolean studentPresent = studentsRepository.findBySkId(insertAverage.getStudentSkId(), tenant).isPresent();
        boolean disciplinePresent = disciplineRepository
                .findDisciplineBySkidAndTenant(insertAverage.getDisciplineSkId(), tenant).isPresent();

        if (!studentPresent) throw new RequestExceptions("Estudante não encontrado!");
        if (!disciplinePresent) throw new RequestExceptions("Disciplina não encontrada!");

        return averageRepository.findByStudentSkIdAndDisciplineSkIdAndEvaluationAndTenant(
                insertAverage.getStudentSkId(),
                insertAverage.getDisciplineSkId(),
                insertAverage.getEvaluation(),
                tenant);
    }

    public List<Average> getAverageByStudent(String studentSkId, int tenant) {
        boolean studentPresent = studentsRepository.findBySkId(studentSkId, tenant).isPresent();
        if (!studentPresent) throw new RequestExceptions("Estudante não encontrado!");

        return averageRepository.findAllByStudentSkIdAndTenant(studentSkId, tenant);
    }

    public String totalAverageByStudent(String studentSkId, int tenant) {
        boolean studentPresent = studentsRepository.findBySkId(studentSkId, tenant).isPresent();
        if (!studentPresent) throw new RequestExceptions("Estudante não encontrado!");

        List<Average> allByStudentSkIdAndTenant = averageRepository.findAllByStudentSkIdAndTenant(studentSkId, tenant);

        double increment = 0.0;
        for (Average average : allByStudentSkIdAndTenant) {
            increment = increment + average.getAverage();
        }
        Double total = increment / allByStudentSkIdAndTenant.size();

        return "Média final: " + total;
    }


    public String update(InsertAverage insertAverage, int tenant) {
        Average average = getByStudentSkIdAndDisciplineSkIdAndAverageAndEvaluationAndTenant(insertAverage, tenant)
                .orElseThrow(() -> new RequestExceptions("Nota do estudante para essa avaliação e disciplina não encontrada!"));
        average.setAverage(insertAverage.getAverage());
        Average save = averageRepository.save(average);
        return save.getSkid();
    }
}
