package io.github.ValterGabriell.FrequenciaAlunos.domain.QRCode.dto;

public class QrCodeMessage {
    private String studentName;
    private String studentId;

    public QrCodeMessage(String studentName, String studentId) {
        this.studentName = studentName;
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    @Override
    public String toString() {
        return "QrCodeMessage{" +
                "studentName='" + studentName + '\'' +
                ", studentId='" + studentId + '\'' +
                '}';
    }
}
