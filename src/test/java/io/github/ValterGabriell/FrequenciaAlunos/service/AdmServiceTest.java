package io.github.ValterGabriell.FrequenciaAlunos.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AdmServiceTest {

    @Test
    public void testDateIsValid(){
        LocalDate now = LocalDate.now();
        LocalDate plusSeven = LocalDate.now().plusDays(7);
        System.out.println(now);
        System.out.println(plusSeven);
        Assertions.assertThat(now).isBefore(plusSeven);
    }
    @Test
    public void testDateIsNotValid(){
        LocalDate now = LocalDate.now().plusDays(7);
        LocalDate plusSeven = LocalDate.now().plusDays(7);
        System.out.println(now);
        System.out.println(plusSeven);
        Assertions.assertThat(now).isBefore(plusSeven);
    }
    @Test
    public void testDateIsNotValidTwo(){
        LocalDate now = LocalDate.now().plusDays(8);
        LocalDate plusSeven = LocalDate.now().plusDays(7);
        System.out.println(now);
        System.out.println(plusSeven);
        Assertions.assertThat(now).isBefore(plusSeven);
    }

}