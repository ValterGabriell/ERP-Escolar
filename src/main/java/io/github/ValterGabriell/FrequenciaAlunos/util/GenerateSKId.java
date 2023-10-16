package io.github.ValterGabriell.FrequenciaAlunos.util;

import java.util.Stack;

public class GenerateSKId {
    public static String generateSkId(String id) {
        char[] charArray = id.toCharArray();
        Stack<Integer> stack = new Stack<>();
        for (Character c : charArray) {
            int i = c.hashCode();
            stack.push(i);
        }
        StringBuilder stringBuilder = new StringBuilder();
        var index = 0;
        while (index < 12) {
            Integer pop = stack.pop();
            stringBuilder.append(pop);
            index++;
        }
        return stringBuilder.toString();
    }
}
