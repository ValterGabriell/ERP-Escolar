package io.github.ValterGabriell.FrequenciaAlunos.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncoder {
    /**
     * Método para codificar a senha usando SHA-256.
     *
     * @param rawPassword A senha em texto plano.
     * @return A senha codificada em formato hexadecimal.
     * @throws RuntimeException Se o algoritmo SHA-256 não estiver disponível.
     */
    public String encode(String rawPassword) {
        try {
            // Instancia o algoritmo de hash SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Calcula o hash da senha
            byte[] encodedHash = digest.digest(rawPassword.getBytes());
            // Converte os bytes em uma string hexadecimal
            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao codificar a senha: Algoritmo SHA-256 não encontrado", e);
        }
    }

    /**
     * Método para comparar uma senha fornecida com a senha armazenada (codificada).
     *
     * @param rawPassword A senha fornecida em texto plano.
     * @param encodedPassword A senha armazenada (codificada).
     * @return true se as senhas coincidirem, caso contrário, false.
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        // Codifica a senha fornecida
        String encodedRawPassword = encode(rawPassword);
        // Compara a senha fornecida com a senha armazenada
        return encodedRawPassword.equals(encodedPassword);
    }

    /**
     * Método auxiliar para converter bytes em uma string hexadecimal.
     *
     * @param bytes O array de bytes.
     * @return Uma string hexadecimal correspondente.
     */
    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
