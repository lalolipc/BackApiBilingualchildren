package com.utn2019.avanzada2.tp9.repository;

import com.utn2019.avanzada2.tp9.domain.Phrase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhraseRepository extends JpaRepository<Phrase, Integer> {
}
