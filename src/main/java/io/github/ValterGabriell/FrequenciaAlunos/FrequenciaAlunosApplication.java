package io.github.ValterGabriell.FrequenciaAlunos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;

import java.awt.image.BufferedImage;

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
