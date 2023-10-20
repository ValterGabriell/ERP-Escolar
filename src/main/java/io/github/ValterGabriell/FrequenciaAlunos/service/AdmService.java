package io.github.ValterGabriell.FrequenciaAlunos.service;

import io.github.ValterGabriell.FrequenciaAlunos.controller.AdmController;
import io.github.ValterGabriell.FrequenciaAlunos.controller.StudentsController;
import io.github.ValterGabriell.FrequenciaAlunos.domain.admins.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.domain.contacts.Contacts;
import io.github.ValterGabriell.FrequenciaAlunos.domain.login.Login;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.AdminRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.ContactsRepository;
import io.github.ValterGabriell.FrequenciaAlunos.infra.repository.LoginRepository;
import io.github.ValterGabriell.FrequenciaAlunos.mapper.admin.*;
import io.github.ValterGabriell.FrequenciaAlunos.util.GenerateSKId;
import io.github.ValterGabriell.FrequenciaAlunos.validation.AdminValidationImpl;
import io.github.ValterGabriell.FrequenciaAlunos.validation.ContactValidationImpl;
import io.github.ValterGabriell.FrequenciaAlunos.validation.DocumentsValidationImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Links;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class AdmService {
    private final AdminRepository adminRepository;
    private final LoginRepository loginRepository;
    private final ContactsRepository contactsRepository;

    public AdmService(AdminRepository adminRepository, LoginRepository loginRepository, ContactsRepository contactsRepository) {
        this.adminRepository = adminRepository;
        this.loginRepository = loginRepository;
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
    private boolean returnIfAdminExistsOnDatabase(String cnpj, Integer tenant) {
        AdminValidationImpl adminValidation = new AdminValidationImpl();
        Admin adminIsPresent = adminValidation.validateIfAdminExistsAndReturnIfExist_ByCnpj(adminRepository, cnpj, tenant);
        return adminIsPresent != null;
    }

    private Login createLogin(Admin admin, int tenant) {
        // Cria um objeto 'Login' para o fluxo de login
        Login login = new Login(
                admin.getEmail(),
                admin.getPassword(),
                tenant
        );

        // Salva o objeto 'Login' no repositório
        Login loginSaved = loginRepository.save(login);

        // Define o campo 'skid' do 'Login' com o ID gerado após a primeira inserção
        loginSaved.setSkid(loginSaved.getId());

        // Salva o objeto 'Login' atualizado com o 'skid' no repositório novamente
        return loginRepository.save(loginSaved);
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
        AdminValidationImpl adminValidation = new AdminValidationImpl();
        DocumentsValidationImpl documentsValidation = new DocumentsValidationImpl();
        ContactValidationImpl contactValidation = new ContactValidationImpl();

        adminValidation.checkIfAdminTenantIdAlreadyExistsAndThrowAnExceptionIfItIs(adminRepository, tenant);
        newAdmin.getContacts().forEach(contact -> contactValidation.verifyIfEmailIsCorrect(contact.getEmail()));

        boolean adminExists =
                returnIfAdminExistsOnDatabase(newAdmin.getCnpj(), tenant);

        if (adminExists) {
            throw new RequestExceptions("Cadastro com CNPJ encontrado!");
        } else {
            //documentsValidation.checkIfAdminCnpjIsCorrect(newAdmin.getCnpj());
            // Converte um objeto 'newAdmin' em um objeto 'admin'
            Admin admin = newAdmin.toAdmin();

            Login login = createLogin(admin, tenant);

            List<Contacts> contacts = new ArrayList<>(admin.getContacts());
            contacts.forEach(contact -> {
                contact.setUserId(admin.getCnpj());
                contact.setTenant(tenant);
            });
            contactsRepository.saveAll(contacts);

            // Define 'skId' do administrador como um espaço em branco
            admin.setSkId(" ");
            // Define o 'tenant' do administrador
            admin.setTenant(tenant);
            // Define o ID do 'Login' associado ao administrador
            admin.setLoginId(login.getSkid());

            // Salva o objeto 'Admin' no repositório
            Admin adminSaved = adminRepository.save(admin);

            // Gera e define 'skId' para o administrador com base no ID
            adminSaved.setSkId(GenerateSKId.generateSkId(adminSaved.getId()));

            // Salva o objeto 'Admin' atualizado com o 'skId' no repositório novamente
            adminRepository.save(adminSaved);

            // Adiciona um link de auto-relacionamento para o administrador
            adminSaved.add(linkTo(methodOn(AdmController.class)
                    .getAdminBySkId(adminSaved.getSkId(), adminSaved.getTenant())).withSelfRel());

            // Retorna o CNPJ do administrador salvo
            return "CNPJ: " + adminSaved.getCnpj();
        }
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
                                        .getAdminBySkId(admin.getCnpj(), admin.getTenant())).withSelfRel())
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
    public GetAdminMapper updateAdminFirstName(String cnpj, UpdateAdminFirstName updateAdminFirstName, Integer tenant) {
        AdminValidationImpl adminValidation = new AdminValidationImpl();
        Admin admin = adminValidation.validateIfAdminExistsAndReturnIfExist_ByCnpj(adminRepository, cnpj, tenant);
        if (admin == null) {
            throw new RequestExceptions("Cadastro com CNPJ não encontrado!");
        }
        admin.setFirstName(updateAdminFirstName.getFirstName());
        adminRepository.save(admin);
        return admin.getAdminMapper();
    }

    public GetAdminMapper updateAdminSecondName(String cnpj, UpdateAdminSecondName updateAdminSecondName, Integer tenant) {
        AdminValidationImpl adminValidation = new AdminValidationImpl();
        Admin admin = adminValidation.validateIfAdminExistsAndReturnIfExist_ByCnpj(adminRepository, cnpj, tenant);
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
        AdminValidationImpl adminValidation = new AdminValidationImpl();
        Admin admin = adminValidation.validateIfAdminExistsAndReturnIfExist_ByCnpj(adminRepository, cnpj, tenant);
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
        AdminValidationImpl adminValidation = new AdminValidationImpl();
        Admin admin = adminValidation.validateIfAdminExistsAndReturnIfExist_ByCnpj(adminRepository, cnpj, tenant);

        admin.add(linkTo(
                methodOn(AdmController.class)
                        .getAllAdmins(Pageable.unpaged())).withRel("Lista de Administradores"));

        admin.add(linkTo(methodOn(StudentsController.class)
                .insertStudentsIntoDatabase(null, admin.getCnpj(), admin.getTenant()))
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
    public String deleteAdminById(String cnpj, Integer tenant) {
        AdminValidationImpl adminValidation = new AdminValidationImpl();
        Admin admin = adminValidation.validateIfAdminExistsAndReturnIfExist_ByCnpj(adminRepository, cnpj, tenant);
        String response;
        if (admin != null) {
            adminRepository.deleteById(admin.getId());
            response = "Usuário " + cnpj + " deletado com sucesso!";
        } else {
            response = "Falha ao deletar o usuário: " + cnpj;
        }
        return response;
    }
}
