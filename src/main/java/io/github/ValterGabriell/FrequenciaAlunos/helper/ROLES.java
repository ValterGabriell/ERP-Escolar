package io.github.ValterGabriell.FrequenciaAlunos.helper;

public enum ROLES {

    ADMIN("admin"),
    PARENT("parent"),
    OPERATOR("op"),
    PROFESSOR("professor");

    private String value;

    ROLES(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
