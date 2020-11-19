package de.oth.regensburg.projektstudium.backend.repository;

import de.oth.regensburg.projektstudium.backend.entity.Signature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignatureRepository extends JpaRepository<Signature, Long> {
}
