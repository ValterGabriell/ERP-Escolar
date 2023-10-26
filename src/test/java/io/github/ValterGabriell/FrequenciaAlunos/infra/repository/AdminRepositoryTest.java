package io.github.ValterGabriell.FrequenciaAlunos.infra.repository;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.domain.Contact;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        savedAdmin.setFirstName("NOME NOVO");

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
                this.adminRepository.findByEmail(savedAdmin.getEmail(), savedAdmin.getTenant());

        Assertions.assertThat(optionalAdmin).isEmpty();

    }

    @Test
    @DisplayName("Find admin by email when successful")
    public void findByEmail_WhenSuccsseful() {
        Admin adminToBeUpdated = createAdmin();

        Admin savedAdmin = this.adminRepository.save(adminToBeUpdated);

        Optional<Admin> optionalAdmin =
                this.adminRepository.findByEmail(savedAdmin.getEmail(), savedAdmin.getTenant());

        Assertions.assertThat(optionalAdmin).isNotEmpty();

    }


    private Admin createAdmin() {
        List<Contact> contacts = new ArrayList<>();
        return new Admin(
                "Jose",
                "123",
                "email@gmail.com",
                "123456789",
                "Carlos",
                contacts
        );
    }

}