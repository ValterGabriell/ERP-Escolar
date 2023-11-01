package io.github.ValterGabriell.FrequenciaAlunos.infra.repository;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ContactsRepository extends JpaRepository<Contact, String> {
}
