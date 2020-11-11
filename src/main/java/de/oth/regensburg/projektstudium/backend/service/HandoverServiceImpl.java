package de.oth.regensburg.projektstudium.backend.service;

import de.oth.regensburg.projektstudium.backend.entity.Handover;
import de.oth.regensburg.projektstudium.backend.entity.Package;
import de.oth.regensburg.projektstudium.backend.exceptions.NotFoundException;
import de.oth.regensburg.projektstudium.backend.repository.HandoverRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        handover.addPackage(pkg);
        packageService.addOrUpdatePackage(pkg);
        return repository.save(handover);
    }
}
