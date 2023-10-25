package io.github.ValterGabriell.FrequenciaAlunos.util.sheet;

import io.github.ValterGabriell.FrequenciaAlunos.domain.students.Student;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.frequency.FrequencyByClass;

import java.time.LocalDate;
import java.util.List;

public interface SheetManipulationContract {
    byte[] createSheet(List<Student> students);

    byte[] createSheet(List<Student> students, LocalDate localDate);
    byte[] createSheetByClass(List<FrequencyByClass> students);
}
