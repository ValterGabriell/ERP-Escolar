package io.github.ValterGabriell.FrequenciaAlunos.service;

import com.google.zxing.WriterException;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Student;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.StudentsRepository;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.qrcode.QrCodeMessage;
import io.github.ValterGabriell.FrequenciaAlunos.util.QRCode.QRCodeGenerate;
import io.github.ValterGabriell.FrequenciaAlunos.validation.StudentValidation;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class QRCodeService {

    private final StudentsRepository studentsRepository;

    public QRCodeService(StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }


    private Student validateIfStudentExistsAndReturnIfExist(String studentSkId, int tenantId) {
        StudentValidation studentValidation = new StudentValidation();
        return studentValidation.validateIfStudentExistsAndReturnIfExist(studentsRepository, studentSkId, tenantId);
    }


    /**
     * Method that create and returns qrcode with student id
     *
     * @param studentSkId representing the student id to put data on qrcode
     * @return qrcode generated as base64
     * @throws WriterException
     */
    public String returnQrCodeAsBase64(String studentSkId, int tenantId) throws WriterException, RequestExceptions {
        Student student = validateIfStudentExistsAndReturnIfExist(studentSkId, tenantId);
        QrCodeMessage qrm = new QrCodeMessage(student.getFirstName(), student.getStudentId());
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

    public BufferedImage returnQrCodeImage(String studentSkId, int tenant) throws WriterException, RequestExceptions {
        Student student = validateIfStudentExistsAndReturnIfExist(studentSkId, tenant);
        QrCodeMessage qrm = new QrCodeMessage(student.getFirstName(), student.getStudentId());
        return QRCodeGenerate.generateQRCodeImage(qrm, 400, 400);
    }

}
