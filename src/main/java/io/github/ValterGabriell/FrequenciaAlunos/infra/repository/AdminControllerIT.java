package io.github.ValterGabriell.FrequenciaAlunos.infra.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;


}
