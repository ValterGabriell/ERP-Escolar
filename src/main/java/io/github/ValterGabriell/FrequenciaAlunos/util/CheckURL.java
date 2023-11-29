package io.github.ValterGabriell.FrequenciaAlunos.util;

import jakarta.servlet.http.HttpServletRequest;

public class CheckURL {
    public static boolean checkURL(HttpServletRequest request) {
        String path = request.getRequestURI();
        String URL = "/api/v1/admin";
        int size = URL.length();
        if (path.length() >= size){
            String substring = path.substring(0, size);
            return substring.equals(URL);
        }
        return false;
    }
}
