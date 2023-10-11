package io.github.ValterGabriell.FrequenciaAlunos.controller;

import io.github.ValterGabriell.FrequenciaAlunos.service.QRCodeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;

@RestController
@RequestMapping("/api/v1/qrcode")
public class QRCodeController {

    private final QRCodeService qrCodeService;

    public QRCodeController(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }
    @GetMapping(value = "generate", params = {"studentId"})
    public ResponseEntity<String> generateQRCodeBase64(@RequestParam String studentId) throws Exception {
        String imageData = qrCodeService.returnQrCodeAsBase64(studentId);
        return ResponseEntity.ok().body(imageData);
    }
    @GetMapping(value = "image", produces = MediaType.IMAGE_PNG_VALUE, params = {"studentId"})
    public ResponseEntity<BufferedImage> generateQRCode(@RequestParam String studentId) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        BufferedImage imageData = qrCodeService.returnQrCodeImage(studentId);
        return ResponseEntity.ok().headers(headers).body(imageData);
    }

}
