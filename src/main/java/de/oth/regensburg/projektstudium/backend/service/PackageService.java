package de.oth.regensburg.projektstudium.backend.service;

import de.oth.regensburg.projektstudium.backend.entity.Package;
import de.oth.regensburg.projektstudium.backend.entity.ShipmentCourse;
import de.oth.regensburg.projektstudium.backend.entity.Signature;

import java.util.List;

public interface PackageService {

    List<Package> findAll();

    Package findOneByIdOrBarcode(String idOrBarcode);

    Package createPackage(Package newPackage);

    Package collectPackage(String idOrBarcode, Long driverId);

    Package deliverPackage(String idOrBarcode, String signature);

    Package rescheduleDelivery(String idOrBarcode);

    Package markNotDeliverable(String idOrBarcode);

    List<ShipmentCourse> findShipmentCoursesByPackageIdOrBarcode(String idOrBarcode);

    Signature findSignatureByPackageIdOrBarcode(String idOrBarcode);
}
