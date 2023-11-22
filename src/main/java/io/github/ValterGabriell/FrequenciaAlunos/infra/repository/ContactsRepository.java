package io.github.ValterGabriell.FrequenciaAlunos.infra.repository;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface ContactsRepository extends JpaRepository<Contact, UUID> {
}
