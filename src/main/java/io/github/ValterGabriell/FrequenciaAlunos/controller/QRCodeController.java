package io.github.ValterGabriell.FrequenciaAlunos.controller;

import io.github.ValterGabriell.FrequenciaAlunos.service.QRCodeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;

@RestController
@RequestMapping("/api/v1/qrcode")
@CrossOrigin(origins = "*")
public class QRCodeController {

    private final QRCodeService qrCodeService;

    public QRCodeController(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }
    @GetMapping(value = "generate", params = {"studentSkId","tenant"})
    public ResponseEntity<String> generateQRCodeBase64(@RequestParam String studentSkId,
                                                       @RequestParam int tenant) throws Exception {
        String imageData = qrCodeService.returnQrCodeAsBase64(studentSkId, tenant);
        return ResponseEntity.ok().body(imageData);
    }
    @GetMapping(value = "image", produces = MediaType.IMAGE_PNG_VALUE, params = {"studentSkId", "tenant"})
    public ResponseEntity<BufferedImage> generateQRCode(@RequestParam String studentSkId,
                                                        @RequestParam int tenant) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        BufferedImage imageData = qrCodeService.returnQrCodeImage(studentSkId, tenant);
        return ResponseEntity.ok().headers(headers).body(imageData);
    }

}
