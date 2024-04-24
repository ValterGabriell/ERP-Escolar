package io.github.ValterGabriell.FrequenciaAlunos.exceptions;

public class ExceptionsValues {
    public static final String ILLEGAL_CPF_LENGTH = "Tamanho do CPF inválido! Correto: 11";
    public static final String ILLEGAL_CNPJ_LENGTH = "Tamanho do CNPJ inválido! Correto: 14";
    public static final String DONT_CONTAINS_ONLY_NUMBERS = "Campo não contem apenas numeros!";
    public static final String USERNAME_NULL = "O nome de usuário não pode ser nulo ou ter espaços em branco!";
    public static final String EMAIL_NULL = "O email de usuário não pode ser nulo!";
    public static final String CPF_NULL = "O cpf de usuário não pode ser nulo ou ter espaços em branco!";
    public static final String USERNAME_ILLEGAL_LENGHT = "O nome de usuário precisa ter 2 ou mais caracteres!";
    public static final String INVALID_BORN_YEAR = "Ano de nascimento deve ser menor que o ano atual";
    public static final String USERNAME_ILLEGAL_CHARS = "O nome de usuário precisa ter apenas letras!";
    public static final String USER_NOT_FOUND = "Estudante não encontrado!";
    public static final String STUDENT_ALREADY_VALIDATED = "Frequência para o dia já validada!";
    public static final String STUDENT_ALREADY_SAVED = "Estudante já cadastrado!";
    public static final String DAY_NOT_FOUND = "Dia nao encontrado na frequencia do aluno";
    public static final String FREQUENCY_NOT_FOUND = "Frequencia nao encontrada";
    public static final String STUDENT_ALREADY_SAVED_TO_ADMINISTRATOR = "Estudante já cadastrado para esse administrador!";
}
