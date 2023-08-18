package io.github.ValterGabriell.FrequenciaAlunos.domain.QRCode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import io.github.ValterGabriell.FrequenciaAlunos.domain.QRCode.dto.QrCodeMessage;

import java.awt.image.BufferedImage;

public class QRCodeGenerate {
    public static BufferedImage generateQRCodeImage(QrCodeMessage messageQrCode, int width, int height) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(messageQrCode.toString(), BarcodeFormat.QR_CODE, width, height);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        return bufferedImage;
    }
}
