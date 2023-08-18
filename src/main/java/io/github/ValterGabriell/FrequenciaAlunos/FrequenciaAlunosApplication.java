package io.github.ValterGabriell.FrequenciaAlunos;

import com.google.zxing.WriterException;
import io.github.ValterGabriell.FrequenciaAlunos.domain.QRCode.QRCodeGenerate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;

import java.awt.image.BufferedImage;
import java.io.IOException;

@SpringBootApplication
public class FrequenciaAlunosApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrequenciaAlunosApplication.class, args);
    }

    @Bean
    public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }
}
