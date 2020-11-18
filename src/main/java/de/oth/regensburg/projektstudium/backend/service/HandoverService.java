package de.oth.regensburg.projektstudium.backend.service;

import de.oth.regensburg.projektstudium.backend.entity.Handover;

import java.util.List;
import java.util.UUID;

public interface HandoverService {
    List<Handover> findAll();

    Handover findOneByUuid(UUID uuid);

    Handover createHandover(UUID handoverUuid, Long driverId);

    Handover addPackage(UUID handoverUuid, String pkgIdOrBarcode);

    Handover confirm(UUID uuid);

    Handover rollback(UUID uuid);
}
