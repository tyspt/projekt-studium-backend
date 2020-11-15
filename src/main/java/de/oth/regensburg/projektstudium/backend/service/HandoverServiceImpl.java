package de.oth.regensburg.projektstudium.backend.service;

import de.oth.regensburg.projektstudium.backend.entity.Driver;
import de.oth.regensburg.projektstudium.backend.entity.Handover;
import de.oth.regensburg.projektstudium.backend.entity.Package;
import de.oth.regensburg.projektstudium.backend.entity.enums.HandoverStatus;
import de.oth.regensburg.projektstudium.backend.entity.enums.PackageStatus;
import de.oth.regensburg.projektstudium.backend.entity.enums.PackageType;
import de.oth.regensburg.projektstudium.backend.exceptions.ForbiddenException;
import de.oth.regensburg.projektstudium.backend.exceptions.GoneException;
import de.oth.regensburg.projektstudium.backend.exceptions.NotFoundException;
import de.oth.regensburg.projektstudium.backend.repository.HandoverRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class HandoverServiceImpl implements HandoverService {

    private static final Logger log = LoggerFactory.getLogger(HandoverServiceImpl.class);
    private final HandoverRepository handoverRepository;
    private final PackageService packageService;
    private final DriverService driverService;

    public HandoverServiceImpl(
            HandoverRepository handoverRepository,
            PackageService packageService,
            DriverService driverService
    ) {
        this.handoverRepository = handoverRepository;
        this.packageService = packageService;
        this.driverService = driverService;
    }

    @Override
    public List<Handover> findAll() {
        return handoverRepository.findAll();
    }

    @Override
    public Handover findOneByUuid(UUID uuid) {
        return handoverRepository.findById(uuid).orElseThrow(
                () -> new NotFoundException(Handover.class));
    }

    @Override
    public Handover addHandover(UUID handoverUuid, Long driverId) {
        final Driver driver = driverService.findOneById(driverId);
        Handover handover = new Handover();
        handover.setUuid(handoverUuid);
        handover.setDriver(driver);
        return handoverRepository.save(handover);
    }

    @Override
    @Transactional
    public Handover addPackage(UUID handoverUuid, String pkgIdOrBarcode) {
        final Handover handover = this.findOneByUuid(handoverUuid);
        final Package pkg = this.packageService.findPackageByIdOrBarcode(pkgIdOrBarcode);

        if (handover.getStatus() != HandoverStatus.ON_GOING) {
            throw new GoneException("Handover " + handoverUuid + " is already completed or cancelled.");
        }

        final boolean isActionValid = (pkg.getType() == PackageType.INBOUND && pkg.getStatus() == PackageStatus.CREATED) ||
                (pkg.getType() == PackageType.OUTBOUND && pkg.getStatus() == PackageStatus.IN_TRANSPORT);
        if (!isActionValid) {
            throw new ForbiddenException("Package #" + pkgIdOrBarcode + " is in wrong status: " + pkg.getStatus() + " (" + pkg.getType().toString().toLowerCase() +
                    "), handover is only allow for inbound packages with CREATED or outbound packages with IN_TRANSPORT status.");
        }

        pkg.setStatus(PackageStatus.IN_HANDOVER);
        handover.addPackage(pkg);
        return handoverRepository.save(handover);
    }

    @Override
    @Transactional
    public Handover confirm(UUID uuid) {
        final Handover handover = this.findOneByUuid(uuid);
        final Collection<Package> packages = handover.getPackages();
        final Driver driver = handover.getDriver();

        for (Package pkg : packages) {
            pkg.setStatus(
                    pkg.getType() == PackageType.INBOUND ?
                            PackageStatus.IN_TRANSPORT :
                            PackageStatus.RECEIVED_BY_LC);
            pkg.setDriver(driver);
        }
        handover.setStatus(HandoverStatus.COMPLETED);
        return handoverRepository.save(handover);
    }

    @Override
    @Transactional
    public Handover rollback(UUID uuid) {
        final Handover handover = this.findOneByUuid(uuid);
        final Collection<Package> packages = handover.getPackages();

        for (Package pkg : packages) {
            pkg.setStatus(
                    pkg.getType() == PackageType.INBOUND ?
                            PackageStatus.CREATED :
                            PackageStatus.IN_TRANSPORT);
            pkg.setHandover(null);
        }

        handover.getPackages().clear();
        handover.setStatus(HandoverStatus.CANCELED);
        return handoverRepository.save(handover);
    }
}
