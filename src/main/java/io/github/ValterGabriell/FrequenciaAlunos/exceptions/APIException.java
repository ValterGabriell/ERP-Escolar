package io.github.ValterGabriell.FrequenciaAlunos.exceptions;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record APIException(String message, HttpStatus httpStatus, ZonedDateTime timestamp, String developerMessage) {
}
