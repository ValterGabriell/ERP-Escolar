package io.github.ValterGabriell.FrequenciaAlunos.util;

import java.util.UUID;

public class GenerateSKId {
    public static String generateSkId() {
        return UUID.randomUUID().toString();
    }
}
