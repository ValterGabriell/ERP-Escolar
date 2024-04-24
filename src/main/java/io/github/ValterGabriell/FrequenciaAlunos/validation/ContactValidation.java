package io.github.ValterGabriell.FrequenciaAlunos.validation;

import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import org.springframework.stereotype.Service;

@Service
public class ContactValidation extends Validation {

    @Override
    public boolean verifyIfEmailIsCorrectAndThrowAnErrorIfIsNot(String email){
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (!email.matches(regex)){
            throw new RequestExceptions("Email inválido");
        }
        return true;
    }
}
