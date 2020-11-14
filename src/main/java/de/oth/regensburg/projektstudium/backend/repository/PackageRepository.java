package de.oth.regensburg.projektstudium.backend.repository;

import de.oth.regensburg.projektstudium.backend.entity.Package;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PackageRepository extends JpaRepository<Package, Long> {

    @EntityGraph(value = "package-graph")
    @Override
    List<Package> findAll();
}
