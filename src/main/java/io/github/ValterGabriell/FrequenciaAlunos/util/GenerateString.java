package io.github.ValterGabriell.FrequenciaAlunos.util;

public class GenerateString {
    public static String generateString(String... strings) {
        StringBuilder sb = Instances.getStringBuilderInstance();
        for (String s : strings) {
            sb.append(s);
        }
        return sb.toString();
    }
}
