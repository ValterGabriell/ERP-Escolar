package io.github.ValterGabriell.FrequenciaAlunos.infra.swagger;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

    private final String devUrl = "https://sfsystem.onrender.com";

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");


        Contact contact = new Contact();
        contact.setEmail("valtergabrielbs13@gmail.com");
        contact.setName("Valter Gabriel");
        contact.setUrl("https://github.com/ValterGabriell");

        Info info = new Info()
                .title("Tutorial Management API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints. To check documentation, go to my Github Page");

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}