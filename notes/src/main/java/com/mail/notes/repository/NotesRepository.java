package com.mail.notes.repository;

import com.mail.notes.domain.EnvelopeLocal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotesRepository extends JpaRepository<EnvelopeLocal,Long> {
}
