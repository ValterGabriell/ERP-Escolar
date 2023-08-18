package io.github.ValterGabriell.FrequenciaAlunos.domain.sheet.dto;

public class ResponseSheet {
    private String sheetName;
    private byte[] sheetByteArray;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public byte[] getSheetByteArray() {
        return sheetByteArray;
    }

    public void setSheetByteArray(byte[] sheetByteArray) {
        this.sheetByteArray = sheetByteArray;
    }
}
