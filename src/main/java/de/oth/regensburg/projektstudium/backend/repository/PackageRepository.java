package de.oth.regensburg.projektstudium.backend.repository;

import de.oth.regensburg.projektstudium.backend.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PackageRepository extends JpaRepository<Package, Long> {

    // Trying to solve the n+1 problem with left joins... But we may need to further optimize the query later
    @Query("select pkg, recipient, rcpbuilding, rcpRepresentative, sender, senderBuilding, senderRepresentative from Package pkg\n" +
            "left join Person recipient on pkg.recipient.id = recipient.id\n" +
            "left join Building rcpbuilding on recipient.building.id= rcpbuilding.id\n" +
            "left join Person rcpRepresentative on recipient.representative.id= rcpRepresentative.id\n" +
            "left join Person sender on pkg.sender.id = sender.id\n" +
            "left join Building senderBuilding on sender.building.id = senderBuilding.id\n" +
            "left join Person senderRepresentative on sender.representative.id= senderRepresentative.id")
    @Override
    List<Package> findAll();
}
