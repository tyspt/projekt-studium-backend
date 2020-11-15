package de.oth.regensburg.projektstudium.backend.service;

import de.oth.regensburg.projektstudium.backend.dto.PackageDTO;
import de.oth.regensburg.projektstudium.backend.entity.Package;

import java.text.ParseException;
import java.util.List;

public interface PackageService {

    List<PackageDTO> findAll();

    PackageDTO findPackageDTOByIdOrBarcode(String idOrBarcode);

    Package findPackageByIdOrBarcode(String idOrBarcode);

    PackageDTO addPackage(PackageDTO newPackage) throws ParseException;

    PackageDTO collectPackage(String idOrBarcode, Long driverId);
}
