package de.oth.regensburg.projektstudium.backend.repository;

import de.oth.regensburg.projektstudium.backend.entity.Handover;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HandoverRepository extends JpaRepository<Handover, UUID> {

}
