package io.github.ValterGabriell.FrequenciaAlunos;

import io.github.ValterGabriell.FrequenciaAlunos.util.PasswordEncoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;

import java.awt.image.BufferedImage;

@SpringBootApplication
@EnableCaching
public class FrequenciaAlunosApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrequenciaAlunosApplication.class, args);
    }

    @Bean
    public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder();
    }
}
