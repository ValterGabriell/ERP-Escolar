package io.github.ValterGabriell.FrequenciaAlunos.excpetion;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record APIException(String message, HttpStatus httpStatus, ZonedDateTime timestamp) {
}
