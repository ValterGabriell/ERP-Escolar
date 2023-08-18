package io.github.ValterGabriell.FrequenciaAlunos.domain.sheet;

import io.github.ValterGabriell.FrequenciaAlunos.domain.HandleDate;
import io.github.ValterGabriell.FrequenciaAlunos.domain.students.Student;
import io.github.ValterGabriell.FrequenciaAlunos.excpetion.RequestExceptions;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static io.github.ValterGabriell.FrequenciaAlunos.domain.HandleDate.getDateFormat;

public class SheetManipulation implements SheetManipulationContract {
    /**
     * create headers for sheet student - NAME, CPF, DATE, PRESENT
     *
     * @param sheetAlunos sheet
     */
    private static void createHeadersOfColumns(HSSFSheet sheetAlunos) {
        List<String> columnTitle = new ArrayList<>();
        columnTitle.add("ALUNO ID");
        columnTitle.add("ALUNO NOME");
        columnTitle.add("DIA");
        columnTitle.add("PRESENTE");

        Row row = sheetAlunos.createRow(0);
        int cellnumber = 0;
        for (String value : columnTitle) {
            Cell cell = row.createCell(cellnumber++);
            cell.setCellValue(value);
        }
    }

    /**
     * fill the sheet with students data
     * @param students    current student to insert on sheet
     * @param sheetAlunos sheet
     */
    private static void createFieldOfColumns(List<Student> students, HSSFSheet sheetAlunos) {
        int rownumber = 1;
        int columnnumber = 0;
        for (Student student : students) {
            Row _row = sheetAlunos.createRow(rownumber++);

            Cell cellCpf = _row.createCell(columnnumber++);
            cellCpf.setCellValue(student.getCpf());

            Cell cellName = _row.createCell(columnnumber++);
            cellName.setCellValue(student.getUsername());

            Cell cellDate = _row.createCell(columnnumber++);
            cellDate.setCellValue(LocalDate.now().toString());

            Cell cellOk = _row.createCell(columnnumber++);
            cellOk.setCellValue("OK");

            columnnumber = 0;
        }
    }

    /**
     * method to and download sheet
     * @param workbook specify type to work with sheets
     * @return byte array that contains sheet
     */
    private static byte[] returnSheetByteArrayToDownloadIt(HSSFWorkbook workbook) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();) {
            workbook.write(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RequestExceptions("Erro interno na criação de planilha");
        }
    }


    @Override
    public byte[] createSheet(List<Student> students) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheetAlunos = workbook.createSheet(HandleDate.getDateFormat() + " - PRESENÇA");
        createHeadersOfColumns(sheetAlunos);
        createFieldOfColumns(students, sheetAlunos);
        return returnSheetByteArrayToDownloadIt(workbook);
    }

    @Override
    public byte[] createSheet(List<Student> students, LocalDate localDate) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheetAlunos = workbook.createSheet(HandleDate.getDateFormat(localDate) + " - PRESENÇA");
        createHeadersOfColumns(sheetAlunos);
        createFieldOfColumns(students, sheetAlunos);
        return returnSheetByteArrayToDownloadIt(workbook);
    }

}