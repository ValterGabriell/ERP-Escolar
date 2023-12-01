package io.github.ValterGabriell.FrequenciaAlunos.util;

import java.util.Random;

public class GenerateTenant {
    public static Integer generateTenant(){
        return new Random().nextInt(0, 999);
    }
}
