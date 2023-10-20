package io.github.ValterGabriell.FrequenciaAlunos.infra.repository;

import io.github.ValterGabriell.FrequenciaAlunos.domain.contacts.Contacts;
import io.github.ValterGabriell.FrequenciaAlunos.domain.login.Login;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ContactsRepository extends JpaRepository<Contacts, String> {
}
