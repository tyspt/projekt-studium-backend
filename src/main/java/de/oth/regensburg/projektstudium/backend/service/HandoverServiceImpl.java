package de.oth.regensburg.projektstudium.backend.service;

import de.oth.regensburg.projektstudium.backend.entity.Package;
import de.oth.regensburg.projektstudium.backend.entity.*;
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
    private final HandoverRepository repository;
    private final PackageService packageService;

    public HandoverServiceImpl(
            HandoverRepository repository,
            PackageService packageService
    ) {
        this.repository = repository;
        this.packageService = packageService;
    }

    @Override
    public List<Handover> findAll() {
        return repository.findAll();
    }

    @Override
    public Handover findOneByUuid(UUID uuid) {
        return repository.findById(uuid).orElseThrow(
                () -> new NotFoundException(Handover.class));
    }

    @Override
    public Handover addHandover(Handover newHandover) {
        return repository.save(newHandover);
    }

    @Override
    @Transactional
    public Handover addPackage(UUID handoverUuid, String pkgIdOrBarcode) {
        final Handover handover = this.findOneByUuid(handoverUuid);
        final Package pkg = this.packageService.findOneByIdOrBarcode(pkgIdOrBarcode);

        if (handover.getStatus() != HandoverStatus.ON_GOING) {
            throw new GoneException("Handover " + handoverUuid + " is already completed or cancelled");
        }

        final boolean isActionValid = (pkg.getStatus() == PackageStatus.CREATED || pkg.getStatus() == PackageStatus.COLLECTED);
        if (!isActionValid) {
            throw new ForbiddenException("Package #" + pkgIdOrBarcode + " is in wrong status: " + pkg.getStatus() + ", handover is not allowed");
        }

        pkg.setStatus(PackageStatus.IN_HANDOVER);
        handover.addPackage(pkg);

        packageService.addOrUpdatePackage(pkg);
        return repository.save(handover);
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
        return repository.save(handover);
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
                            PackageStatus.COLLECTED);
            pkg.setHandover(null);
        }

        handover.getPackages().clear();
        handover.setStatus(HandoverStatus.CANCELED);
        return repository.save(handover);
    }
}
