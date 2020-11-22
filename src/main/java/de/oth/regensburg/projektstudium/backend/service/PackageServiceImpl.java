package de.oth.regensburg.projektstudium.backend.service;

import de.oth.regensburg.projektstudium.backend.entity.Driver;
import de.oth.regensburg.projektstudium.backend.entity.Package;
import de.oth.regensburg.projektstudium.backend.entity.ShipmentCourse;
import de.oth.regensburg.projektstudium.backend.entity.Signature;
import de.oth.regensburg.projektstudium.backend.entity.enums.PackageStatus;
import de.oth.regensburg.projektstudium.backend.entity.enums.PackageType;
import de.oth.regensburg.projektstudium.backend.exceptions.ForbiddenException;
import de.oth.regensburg.projektstudium.backend.exceptions.NotFoundException;
import de.oth.regensburg.projektstudium.backend.repository.PackageRepository;
import de.oth.regensburg.projektstudium.backend.repository.SignatureRepository;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PackageServiceImpl implements PackageService {

    private static final Logger log = LoggerFactory.getLogger(PackageService.class);
    private final PackageRepository packageRepository;
    private final ShipmentCourseService shipmentCourseService;
    private final DriverService driverService;
    private final SignatureRepository signatureRepository;

    public PackageServiceImpl(
            PackageRepository packageRepository,
            SignatureRepository signatureRepository,
            ShipmentCourseService shipmentCourseService,
            DriverService driverService
    ) {
        this.packageRepository = packageRepository;
        this.signatureRepository = signatureRepository;
        this.shipmentCourseService = shipmentCourseService;
        this.driverService = driverService;
    }

    @Override
    public List<Package> findAll() {
        return packageRepository.findAll();
    }

    @Override
    public Package findOneByIdOrBarcode(String idOrBarcode) {
        final Long longId = NumberUtils.isParsable(idOrBarcode) ? Long.valueOf(idOrBarcode) : null;
        return packageRepository.findOneByIdOrBarcode(longId, idOrBarcode)
                .orElseThrow(() -> new NotFoundException(Package.class));
    }

    @Override
    @Transactional
    public Package createPackage(Package newPackage) {
        newPackage.addShipmentCourse(shipmentCourseService.createShipmentCourse("Package is initially created."));
        return packageRepository.save(newPackage);
    }

    @Override
    @Transactional
    public Package collectPackage(String idOrBarcode, Long driverId) {
        final Package dbPackage = this.findOneByIdOrBarcode(idOrBarcode);
        final Driver driver = this.driverService.findOneById(driverId);

        if (dbPackage.getType() != PackageType.OUTBOUND || dbPackage.getStatus() != PackageStatus.CREATED) {
            throw new ForbiddenException("Package #" + idOrBarcode + " has wrong type or in wrong status: " + dbPackage.getStatus() + " (" + dbPackage.getType().toString().toLowerCase() +
                    "), collect is only allow for outbound packages with CREATED status.");
        }

        dbPackage.setDriver(driver);
        dbPackage.setStatus(PackageStatus.IN_TRANSPORT);
        dbPackage.addShipmentCourse(shipmentCourseService.createShipmentCourse(
                "Package is collected by driver " + driver.getName() + "of " + driver.getCompany() + "."));
        return packageRepository.save(dbPackage);
    }

    @Override
    @Transactional
    public Package deliverPackage(String idOrBarcode, String signatureData) {
        final Package dbPackage = this.findOneByIdOrBarcode(idOrBarcode);
        signatureRepository.save(new Signature(dbPackage, signatureData));

        dbPackage.setStatus(PackageStatus.DELIVERED);
        dbPackage.addShipmentCourse(shipmentCourseService.createShipmentCourse("Package is delivered."));
        return packageRepository.save(dbPackage);
    }

    @Override
    @Transactional
    public Package rescheduleDelivery(String idOrBarcode) {
        final Package dbPackage = this.findOneByIdOrBarcode(idOrBarcode);
        dbPackage.setStatus(PackageStatus.REATTEMPT_DELIVERY);
        dbPackage.addShipmentCourse(shipmentCourseService.createShipmentCourse("Package is preparing for another delivery attempt."));
        return packageRepository.save(dbPackage);
    }

    @Override
    @Transactional
    public Package markNotDeliverable(String idOrBarcode) {
        final Package dbPackage = this.findOneByIdOrBarcode(idOrBarcode);
        dbPackage.setStatus(PackageStatus.NOT_DELIVERABLE);
        dbPackage.addShipmentCourse(shipmentCourseService.createShipmentCourse("Package is marked as not deliverable."));
        return packageRepository.save(dbPackage);
    }

    @Override
    public List<ShipmentCourse> findShipmentCoursesByPackageIdOrBarcode(String idOrBarcode) {
        final Package pkg = this.findOneByIdOrBarcode(idOrBarcode);
        return pkg.getShipmentCourses();
    }

    @Override
    public Signature findSignatureByPackageIdOrBarcode(String idOrBarcode) {
        final Package pkg = this.findOneByIdOrBarcode(idOrBarcode);
        return signatureRepository.findById(pkg.getId())
                .orElseThrow(() -> new NotFoundException(Signature.class));
    }
}
