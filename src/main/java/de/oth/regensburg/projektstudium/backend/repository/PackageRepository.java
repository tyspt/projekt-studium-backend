package de.oth.regensburg.projektstudium.backend.repository;

import de.oth.regensburg.projektstudium.backend.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<Package, Long> {
}
