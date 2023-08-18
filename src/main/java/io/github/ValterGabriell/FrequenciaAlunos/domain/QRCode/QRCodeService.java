package io.github.ValterGabriell.FrequenciaAlunos.domain.QRCode;

import com.google.zxing.WriterException;
import io.github.ValterGabriell.FrequenciaAlunos.domain.QRCode.dto.QrCodeMessage;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Validation;
import io.github.ValterGabriell.FrequenciaAlunos.domain.students.Student;
import io.github.ValterGabriell.FrequenciaAlunos.excpetion.ExceptionsValues;
import io.github.ValterGabriell.FrequenciaAlunos.excpetion.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.StudentsRepository;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class QRCodeService extends Validation {

    private final StudentsRepository studentsRepository;

    public QRCodeService(StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }

    /**
     * Method that create and returns qrcode with student id
     * @param studentId representing the student id to put data on qrcode
     * @return qrcode generated as base64
     * @throws WriterException
     */
    public String returnQrCodeAsBase64(String studentId) throws WriterException, RequestExceptions {
        if (studentId.length() != 11) {
            throw new RequestExceptions(ExceptionsValues.ILLEGAL_CPF_LENGTH);
        }
        Student student = validateIfStudentExistsAndReturnIfExist(studentsRepository, studentId);
        QrCodeMessage qrm = new QrCodeMessage(student.getUsername(), student.getCpf());
        BufferedImage bufferedImage = QRCodeGenerate.generateQRCodeImage(qrm, 400, 400);
        String imageAsBase64;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "png", baos);
            imageAsBase64 = Base64.getEncoder().encodeToString(baos.toByteArray());
            System.out.println(imageAsBase64);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageAsBase64;
    }

    public BufferedImage returnQrCodeImage(String studentId) throws WriterException, RequestExceptions {
        if (studentId.length() != 11) {
            throw new RequestExceptions(ExceptionsValues.ILLEGAL_CPF_LENGTH);
        }
        Student student = validateIfStudentExistsAndReturnIfExist(studentsRepository, studentId);
        QrCodeMessage qrm = new QrCodeMessage(student.getUsername(), student.getCpf());
        return QRCodeGenerate.generateQRCodeImage(qrm, 400, 400);
    }

}
