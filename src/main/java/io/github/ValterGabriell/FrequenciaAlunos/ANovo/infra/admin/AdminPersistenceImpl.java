package io.github.ValterGabriell.FrequenciaAlunos.ANovo.infra.admin;

import io.github.ValterGabriell.FrequenciaAlunos.ANovo.infra.repository.admin.AdminRepository_V2;
import io.github.ValterGabriell.FrequenciaAlunos.ANovo.ports.admin.IPersistenceAdminPort;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.dto.admin.DtoCreateNewAdmin;
import io.github.ValterGabriell.FrequenciaAlunos.exceptions.RequestExceptions;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminPersistenceImpl implements IPersistenceAdminPort {

    private final AdminRepository_V2 adminRepositoryV2;

    public AdminPersistenceImpl(AdminRepository_V2 adminRepositoryV2) {
        this.adminRepositoryV2 = adminRepositoryV2;
    }

    @Override
    public String create(DtoCreateNewAdmin dtoCreateNewAdmin) {
        return "";
    }

    @Override
    public void checkIfCnpjAlreadyExistAndThrowAnErrorIfItIs(String cnpj) throws RequestExceptions {
        Optional<Admin> admin = adminRepositoryV2.findByCnpj(cnpj);
        if (admin.isPresent()) throw new RequestExceptions("CNPJ já cadastrado no sistema!");
    }

    @Override
    public boolean findByTenant(Integer tenant) {
        return adminRepositoryV2.findByTenant(tenant).isPresent();
    }
}
