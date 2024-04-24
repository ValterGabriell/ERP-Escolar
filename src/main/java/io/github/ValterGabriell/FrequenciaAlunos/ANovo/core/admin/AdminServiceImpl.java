package io.github.ValterGabriell.FrequenciaAlunos.ANovo.core.admin;

import io.github.ValterGabriell.FrequenciaAlunos.ANovo.ports.admin.IPersistenceAdminPort;
import io.github.ValterGabriell.FrequenciaAlunos.ANovo.ports.admin.IServiceAdminPort;
import io.github.ValterGabriell.FrequenciaAlunos.dto.admin.DtoCreateNewAdmin;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.util.GenerateTenant;
import io.github.ValterGabriell.FrequenciaAlunos.validation.ContactValidation;
import io.github.ValterGabriell.FrequenciaAlunos.validation.FieldValidation;

public class AdminServiceImpl implements IServiceAdminPort {

    private final FieldValidation fieldValidation;
    private final ContactValidation contactValidation;
    private final IPersistenceAdminPort iPersistenceAdminPort;

    public AdminServiceImpl(FieldValidation fieldValidation, ContactValidation contactValidation, IPersistenceAdminPort iPersistenceAdminPort) {
        this.fieldValidation = fieldValidation;
        this.contactValidation = contactValidation;
        this.iPersistenceAdminPort = iPersistenceAdminPort;
    }

    /**
     * metodo pra criar um novo admininsitrador do sistema
     * @param dtoCreateNewAdmin DTO responsavel por criar um novo admin
     * @return ID do admin criado
     */
    @Override
    public String create(DtoCreateNewAdmin dtoCreateNewAdmin) {
        /** qualquer erro dentro do método lancará exceção, por isso não há retorno**/
        fieldValidation.validatingFieldsToCreateNewAdmin(dtoCreateNewAdmin);
        /** verifica se CNPJ ja ta cadastrado no sistema **/
        checkIfCnpjAlreadyExistAndThrowAnErrorIfItIs(dtoCreateNewAdmin.getCnpj());
        /** validando emails **/
        dtoCreateNewAdmin.getContacts().forEach(contact -> contactValidation
                .verifyIfEmailIsCorrectAndThrowAnErrorIfIsNot(contact.getEmail()));



        return iPersistenceAdminPort.create(dtoCreateNewAdmin);
    }

    @Override
    public void checkIfCnpjAlreadyExistAndThrowAnErrorIfItIs(String cnpj) throws RequestExceptions {
        iPersistenceAdminPort.checkIfCnpjAlreadyExistAndThrowAnErrorIfItIs(cnpj);
    }

    /**
     * Retorna se o usuario ja existe baseado no tenant dele
     * @param tenant
     * @return se o usuario existe ou nao
     */
    @Override
    public boolean findByTenant(Integer tenant) {
        return iPersistenceAdminPort.findByTenant(tenant);
    }


    /**
     * Cria o tenant, verifica se o tenant ja existe na base e, caso exista, recria o tenant
     * @return tenant
     */
    private Integer generateTenant() {
        var tenant = GenerateTenant.generateTenant();
        boolean tenantExists = findByTenant(tenant);
        if (tenantExists) {
            return generateTenant();
        }
        return tenant;
    }

}
