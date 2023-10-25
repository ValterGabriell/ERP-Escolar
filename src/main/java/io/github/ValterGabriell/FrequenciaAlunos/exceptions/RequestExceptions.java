package io.github.ValterGabriell.FrequenciaAlunos.exceptions;

public class RequestExceptions extends RuntimeException {

    private String developerMessage;

    public RequestExceptions(String message) {
        super(message);
        this.developerMessage = "Check the Documentation";
    }

    public RequestExceptions(String message, String developerMessage) {
        super(message);
        this.developerMessage = developerMessage;

    }

    public String getDeveloperMessage() {
        return developerMessage;
    }
}
