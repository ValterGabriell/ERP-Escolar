package io.github.ValterGabriell.FrequenciaAlunos.util.sheet;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Turma;

import java.time.LocalDate;
import java.util.List;

public interface SheetManipulationContract {
    byte[] createSheet(List<Turma> turmas);

    byte[] createSheet(List<Turma> turmas, LocalDate localDate);
}
