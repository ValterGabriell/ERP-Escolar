package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.controller.AdmController;
import io.github.ValterGabriell.FrequenciaAlunos.controller.StudentsController;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Contact;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Professor;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.helper.roles.ROLES;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.AdminRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.ContactsRepository;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.admin.*;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.professor.ProfessorGet;
import io.github.ValterGabriell.FrequenciaAlunos.util.GenerateSKId;
import io.github.ValterGabriell.FrequenciaAlunos.validation.AdminValidation;
import io.github.ValterGabriell.FrequenciaAlunos.validation.ContactValidation;
import io.github.ValterGabriell.FrequenciaAlunos.validation.FieldValidation;
import io.github.ValterGabriell.FrequenciaAlunos.validation.Validation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Links;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class AdmService {
    private final AdminRepository adminRepository;
    private final ContactsRepository contactsRepository;
    private final AdminValidation adminValidation = new AdminValidation();
    private final ContactValidation contactValidation = new ContactValidation();
    private final FieldValidation fieldValidation = new FieldValidation();

    public AdmService(AdminRepository adminRepository, ContactsRepository contactsRepository) {
        this.adminRepository = adminRepository;
        this.contactsRepository = contactsRepository;
    }

    /**
     * Este método busca um administrador (Admin) pelo identificador `skId` e inquilino (tenant) especificados.
     * Realiza as seguintes etapas:
     * 1. Chama um método de validação para verificar se o administrador existe com base no `skId` e inquilino.
     * 2. Se o administrador não for encontrado, lança uma exceção indicando que o usuário não foi encontrado.
     * 3. Se o administrador for encontrado, retorna a instância do administrador.
     *
     * @param cnpj   O identificador único do administrador a ser encontrado.
     * @param tenant O inquilino associado ao administrador.
     * @return A instância do administrador se encontrado.
     * @throws RequestExceptions Se o administrador com o `skId` especificado não for encontrado.
     */
    private boolean returnIfAdminExistsOnDatabase(String cnpj, Integer tenant, Validation validation) {
        Admin adminIsPresent =
                validation.validateIfAdminExistsAndReturnIfExistByCnpj(adminRepository, cnpj, tenant);
        return adminIsPresent != null;
    }

    /**
     * Este método cria um novo administrador (Admin) com base nos dados fornecidos por `newAdmin` e o associa a um inquilino (tenant) especificado.
     * Antes de criar o novo administrador, ele verifica se já existe um cadastro com o mesmo CNPJ no inquilino especificado.
     * Se um cadastro com o mesmo CNPJ for encontrado, lança uma exceção.
     * Caso contrário, o método realiza as seguintes etapas:
     * 1. Valida se o CNPJ do administrador é correto.
     * 2. Converte `newAdmin` para uma instância da classe `Admin`.
     * 3. Gera um SKId (um identificador único) para o novo administrador.
     * 4. Define o inquilino do novo administrador.
     * 5. Salva o administrador no repositório.
     * 6. Adiciona um link de auto-relação ao administrador salvo para futuras referências.
     * 7. Retorna os links do administrador como uma representação em formato de string.
     *
     * @param newAdmin Os dados do novo administrador a serem criados.
     * @param tenant   O inquilino ao qual o novo administrador será associado.
     * @return Uma representação em formato de string dos links do administrador recém-criado.
     * @throws RequestExceptions Se um cadastro com o mesmo CNPJ for encontrado.
     */
    @Transactional
    public String createNewAdmin(CreateNewAdmin newAdmin, Integer tenant) {
        if (!fieldValidation.fieldContainsOnlyNumbers(newAdmin.getCnpj()))
            throw new RequestExceptions("CNPJ precisa conter apenas numeros");

        adminValidation.checkIfAdminTenantIdAlreadyExistsAndThrowAnExceptionIfItIs(adminRepository, tenant);
        newAdmin.getContacts().forEach(contact -> contactValidation
                .verifyIfEmailIsCorrectAndThrowAnErrorIfIsNot(contact.getEmail()));

        boolean adminExists =
                returnIfAdminExistsOnDatabase(newAdmin.getCnpj(), tenant, adminValidation);

        if (adminExists) {
            throw new RequestExceptions("Cadastro com CNPJ encontrado!");
        } else {
            Admin admin = newAdmin.toAdmin();

            List<Contact> contacts = setAdminIdAndTenantToContacts(tenant, admin);
            List<ROLES> roles = new ArrayList<>();
            roles.add(ROLES.ADMIN);
            roles.add(ROLES.PARENT);
            roles.add(ROLES.PROFESSOR);

            admin.setTenant(tenant);
            admin.setSkId(GenerateSKId.generateSkId());
            admin.setRoles(roles);

            contactsRepository.saveAll(contacts);
            adminRepository.save(admin);

            admin.add(linkTo(methodOn(AdmController.class)
                    .getAdminByCnpj(admin.getSkId(), 0)).withSelfRel());

            return "CNPJ: " + admin.getCnpj();
        }
    }

    private static List<Contact> setAdminIdAndTenantToContacts(Integer tenant, Admin admin) {
        List<Contact> contacts = new ArrayList<>(admin.getContacts());
        contacts.forEach(contact -> {
            contact.setUserId(admin.getCnpj());
            contact.setTenant(tenant);
        });
        return contacts;
    }

    /**
     * Este método recupera uma página de administradores (Admins) com base nas configurações de
     * paginação especificadas em `pageable`.
     * Ele realiza as seguintes etapas:
     * 1. Chama o método `findAll` no repositório para recuperar uma página de administradores.
     * 2. Mapeia cada administrador para um `GetAdminMapper` e adiciona um link de auto-relação a cada um.
     * 3. Coleta os resultados em uma lista.
     * 4. Cria uma página de resultados usando a lista coletada e os parâmetros de paginação.
     *
     * @param pageable As configurações de paginação, incluindo número de página, tamanho da página, ordenação, etc.
     * @return Uma página de `GetAdminMapper` que contém os administradores mapeados e os links de auto-relação.
     */
    public Page<GetAdminMapper> getAllAdmins(Pageable pageable) {
        Page<Admin> adminList = adminRepository.findAll(pageable);

        List<GetAdminMapper> collect =
                adminList
                        .stream()
                        .map(admin -> admin
                                .add(linkTo(methodOn(AdmController.class)
                                        .getAdminByCnpj(admin.getCnpj(), admin.getTenant())).withSelfRel())
                                .getAdminMapper()
                        )
                        .toList();

        Page<GetAdminMapper> page = new PageImpl<>(collect);
        return page;
    }

    /**
     * Este método atualiza o nome de usuário de um administrador (Admin) identificado pelo `cnpj`, associado a um inquilino (tenant) especificado.
     * Realiza as seguintes etapas:
     * 1. Encontra o administrador com o `cnpj` especificado ou lança uma exceção se não for encontrado.
     * 2. Atualiza o nome de usuário do administrador com o valor fornecido em `updateAdminUsername`.
     * 3. Salva as alterações no repositório.
     * 4. Retorna uma instância de `GetAdminMapper` que representa o administrador atualizado.
     *
     * @param cnpj                 O identificador único do administrador a ser atualizado.
     * @param updateAdminFirstName O novo nome de usuário a ser definido para o administrador.
     * @param tenant               O inquilino associado ao administrador.
     * @return Uma instância de `GetAdminMapper` que representa o administrador atualizado.
     */

    @Transactional
    public GetAdminMapper updateAdminFirstName(String cnpj, UpdateAdminFirstName updateAdminFirstName, Integer tenant) {
        if (!fieldValidation.fieldContainsOnlyNumbers(cnpj))
            throw new RequestExceptions("CNPJ precisa conter apenas numeros");
        Admin admin = adminValidation.validateIfAdminExistsAndReturnIfExistByCnpj(adminRepository, cnpj, tenant);
        if (admin == null) {
            throw new RequestExceptions("Cadastro com CNPJ não encontrado!");
        }
        admin.setFirstName(updateAdminFirstName.getFirstName());
        adminRepository.save(admin);
        return admin.getAdminMapper();
    }

    public GetAdminMapper updateAdminSecondName(String cnpj, UpdateAdminSecondName updateAdminSecondName, Integer tenant) {
        if (!fieldValidation.fieldContainsOnlyNumbers(cnpj))
            throw new RequestExceptions("CNPJ precisa conter apenas numeros");
        Admin admin = adminValidation.validateIfAdminExistsAndReturnIfExistByCnpj(adminRepository, cnpj, tenant);
        if (admin == null) {
            throw new RequestExceptions("Cadastro com CNPJ não encontrado!");
        }
        admin.setSecondName(updateAdminSecondName.getSecondName());
        adminRepository.save(admin);
        return admin.getAdminMapper();
    }


    /**
     * Este método atualiza a senha de um administrador (Admin) identificado pelo `cnpj`, associado a um inquilino (tenant) especificado.
     * Realiza as seguintes etapas:
     * 1. Encontra o administrador com o `cnpj` especificado ou lança uma exceção se não for encontrado.
     * 2. Atualiza a senha do administrador com o valor fornecido em `updateAdminPassword`.
     * 3. Salva as alterações no repositório.
     * 4. Retorna uma instância de `GetAdminMapper` que representa o administrador atualizado.
     *
     * @param cnpj                O identificador único do administrador a ser atualizado.
     * @param updateAdminPassword A nova senha a ser definida para o administrador.
     * @param tenant              O inquilino associado ao administrador.
     * @return Uma instância de `GetAdminMapper` que representa o administrador atualizado.
     */
    public GetAdminMapper updateAdminPassword(String cnpj, UpdateAdminPassword updateAdminPassword, Integer tenant) {
        if (!fieldValidation.fieldContainsOnlyNumbers(cnpj))
            throw new RequestExceptions("CNPJ precisa conter apenas numeros");
        Admin admin = adminValidation.validateIfAdminExistsAndReturnIfExistByCnpj(adminRepository, cnpj, tenant);
        if (admin == null) {
            throw new RequestExceptions("Cadastro com CNPJ não encontrado!");
        }
        admin.setPassword(updateAdminPassword.getPassword());
        adminRepository.save(admin);
        return admin.getAdminMapper();
    }


    /**
     * Este método recupera os detalhes de um administrador (Admin) identificado pelo `cnpj`, associado a um inquilino (tenant) especificado, juntamente com links para ações relacionadas.
     * Realiza as seguintes etapas:
     * 1. Encontra o administrador com o `cnpj` especificado ou lança uma exceção se não for encontrado.
     * 2. Adiciona links de auto-relação e links para ações relacionadas ao administrador, como atualização de nome de usuário, senha e exclusão.
     * 3. Retorna uma instância de `GetAdminMapper` que representa o administrador com links para ações relacionadas.
     *
     * @param cnpj   O identificador único do administrador a ser recuperado.
     * @param tenant O inquilino associado ao administrador.
     * @return Uma instância de `GetAdminMapper` que representa o administrador com links para ações relacionadas.
     */
    public GetAdminMapper getAdminByCnpj(String cnpj, Integer tenant) {
        if (!fieldValidation.fieldContainsOnlyNumbers(cnpj))
            throw new RequestExceptions("CNPJ precisa conter apenas numeros");
        Admin admin = adminValidation.validateIfAdminExistsAndReturnIfExistByCnpj(adminRepository, cnpj, tenant);

        admin.add(linkTo(
                methodOn(AdmController.class)
                        .getAllAdmins(Pageable.unpaged())).withRel("Lista de Administradores"));

        admin.add(linkTo(methodOn(StudentsController.class)
                .insertStudentsIntoDatabase(null, admin.getCnpj(), admin.getTenant(), ""))
                .withRel("Inserir novo estudante"));


        Links links = admin.getLinks();
        GetAdminMapper adminMapper = admin.getAdminMapper();
        adminMapper.setLinks(links);
        return adminMapper;
    }


    /**
     * Este método exclui um administrador (Admin) identificado pelo `cnpj`, associado a um inquilino (tenant) especificado.
     * Realiza as seguintes etapas:
     * 1. Encontra o administrador com o `cnpj` especificado ou retorna uma mensagem de falha se não for encontrado.
     * 2. Se o administrador for encontrado, ele é excluído do repositório.
     * 3. Retorna uma mensagem indicando o resultado da operação de exclusão.
     *
     * @param cnpj   O identificador único do administrador a ser excluído.
     * @param tenant O inquilino associado ao administrador.
     * @return Uma mensagem indicando o resultado da exclusão.
     */
    public String deleteAdminByCnpj(String cnpj, Integer tenant) {
        if (!fieldValidation.fieldContainsOnlyNumbers(cnpj))
            throw new RequestExceptions("CNPJ precisa conter apenas numeros");
        Admin admin = adminValidation.validateIfAdminExistsAndReturnIfExistByCnpj(adminRepository, cnpj, tenant);
        String response;
        if (admin != null) {
            adminRepository.deleteById(admin.getAdminId());
            response = "Usuário " + cnpj + " deletado com sucesso!";
        } else {
            response = "Falha ao deletar o usuário: " + cnpj;
        }
        return response;
    }

    public List<ProfessorGet> getAllProfessorsByCnpj(String cnpj, int tenant) {
        if (!fieldValidation.fieldContainsOnlyNumbers(cnpj))
            throw new RequestExceptions("CNPJ precisa conter apenas numeros");
        Admin admin =
                adminRepository.findByCnpj(cnpj, tenant)
                        .orElseThrow(() -> new RequestExceptions("Admin não encontrado"));

        Function<Professor, ProfessorGet> professorProfessorGetFunction = (professor) -> new ProfessorGet(
                professor.getSkid(), professor.getFirstName(), professor.getLastName(), professor.getAverage(),
                professor.getIdentifierNumber(), professor.getTenant(), professor.getStartDate(),
                professor.getFinishedDate(), professor.getContacts()

        );
        return admin.getProfessors().stream().map(professorProfessorGetFunction).toList();
    }
}
