package de.oth.regensburg.projektstudium.backend.service;

import de.oth.regensburg.projektstudium.backend.entity.Package;
import de.oth.regensburg.projektstudium.backend.entity.*;
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

//        // TODO: Remove status check for now, add back later when needed
//        final boolean isActionValid =
//                (pkg.getType() == PackageType.INBOUND && pkg.getStatus() == PackageStatus.CREATED) ||
//                        (pkg.getType() == PackageType.OUTBOUND && pkg.getStatus() == PackageStatus.COLLECTED);
//        if (!isActionValid) {
//            throw new InvalidRequestException("Package is in wrong status, action is not allowed");
//        }

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

        for (Package pkg : packages) {
            pkg.setStatus(
                    pkg.getType() == PackageType.INBOUND ?
                            PackageStatus.IN_TRANSPORT :
                            PackageStatus.RECEIVED_BY_LOGISTIC_CENTER);
        }
        handover.setStatus(HandoverStatus.COMPLETED);

        packageService.saveAll(packages);
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

        packageService.saveAll(packages);
        return repository.save(handover);
    }
}
