package de.oth.regensburg.projektstudium.backend.repository;

import de.oth.regensburg.projektstudium.backend.entity.Package;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PackageRepository extends JpaRepository<Package, Long> {

    @EntityGraph(value = "package-graph")
    @Override
    List<Package> findAll();

    @EntityGraph(value = "package-graph")
    @Query("SELECT p FROM Package p WHERE p.id = ?1 or p.barcode = ?2")
    Optional<Package> findOneByIdOrBarcode(Long id, String idOrBarcode);
}
