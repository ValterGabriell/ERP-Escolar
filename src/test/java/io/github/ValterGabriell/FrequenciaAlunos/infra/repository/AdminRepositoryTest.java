package io.github.ValterGabriell.FrequenciaAlunos.infra.repository;

import io.github.ValterGabriell.FrequenciaAlunos.domain.admins.Admin;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@DisplayName("Tests for Admin Repository")
class AdminRepositoryTest {

    @Autowired
    private AdminRepository adminRepository;

    @Test
    @DisplayName("Save admin when successful")
    public void save_PersistAdmin_WhenSuccsseful() {
        Admin adminToBeSaved = createAdmin();

        Admin savedAdmin = this.adminRepository.save(adminToBeSaved);

        Assertions.assertThat(savedAdmin).isNotNull();

        Assertions.assertThat(savedAdmin.getCnpj()).isEqualTo(adminToBeSaved.getCnpj());
    }

    @Test
    @DisplayName("Update admin when successful")
    public void update_PersistAdmin_WhenSuccsseful() {
        Admin adminToBeSaved = createAdmin();

        Admin savedAdmin = this.adminRepository.save(adminToBeSaved);

        savedAdmin.setUsername("NOME NOVO");

        Admin updatedAdmin = this.adminRepository.save(savedAdmin);

        Assertions.assertThat(updatedAdmin).isNotNull();

        Assertions.assertThat(updatedAdmin.getCnpj()).isEqualTo(savedAdmin.getCnpj());
    }

    @Test
    @DisplayName("Delete admin when successful")
    public void delete_PersistAdmin_WhenSuccsseful() {
        Admin adminToBeUpdated = createAdmin();

        Admin savedAdmin = this.adminRepository.save(adminToBeUpdated);

        this.adminRepository.delete(savedAdmin);

        Optional<Admin> optionalAdmin =
                this.adminRepository.findByEmail(savedAdmin.getEmail());

        Assertions.assertThat(optionalAdmin).isEmpty();

    }

    @Test
    @DisplayName("Find admin by email when successful")
    public void findByEmail_WhenSuccsseful() {
        Admin adminToBeUpdated = createAdmin();

        Admin savedAdmin = this.adminRepository.save(adminToBeUpdated);

        Optional<Admin> optionalAdmin =
                this.adminRepository.findByEmail(savedAdmin.getEmail());

        Assertions.assertThat(optionalAdmin).isNotEmpty();

    }


    private Admin createAdmin() {
        return new Admin(
                UUID.randomUUID().toString(),
                "Jose Carlos",
                "123",
                "email@gmail.com",
                "123456789"
        );
    }

}