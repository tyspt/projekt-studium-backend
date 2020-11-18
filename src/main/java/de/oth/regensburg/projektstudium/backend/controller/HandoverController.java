package de.oth.regensburg.projektstudium.backend.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import de.oth.regensburg.projektstudium.backend.entity.Handover;
import de.oth.regensburg.projektstudium.backend.exceptions.BadRequestException;
import de.oth.regensburg.projektstudium.backend.service.HandoverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("handovers")
public class HandoverController {
    private static final Logger log = LoggerFactory.getLogger(HandoverController.class);
    private final HandoverService handoverService;

    public HandoverController(HandoverService handoverService) {
        this.handoverService = handoverService;
    }

    @GetMapping("")
    List<Handover> findAll() {
        return handoverService.findAll();
    }

    @GetMapping("/{uuid}")
    Handover findOneByUuid(@PathVariable("uuid") UUID uuid) {
        return handoverService.findOneByUuid(uuid);
    }

    @PostMapping("")
    ResponseEntity<Handover> addHandover(@RequestHeader("Driver-ID") String driverId,
                                         @RequestBody ObjectNode handoverUuidJson) {
        if (handoverUuidJson == null || !handoverUuidJson.has("uuid") || StringUtils.isEmpty(driverId)) {
            throw new BadRequestException();
        }

        final UUID uHandoverUuid;
        final Long lDriverId;
        try {
            uHandoverUuid = UUID.fromString(handoverUuidJson.get("uuid").asText());
            lDriverId = Long.valueOf(driverId);
        } catch (Exception e) {
            throw new BadRequestException();
        }

        return new ResponseEntity<>(handoverService.createHandover(uHandoverUuid, lDriverId),
                HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    Handover addPackage(@PathVariable("uuid") UUID uuid,
                        @RequestBody ObjectNode idOrBarcodeJson) {
        if (idOrBarcodeJson == null || !idOrBarcodeJson.has("idOrBarcode")) {
            throw new BadRequestException();
        }
        final String idOrBarcode = idOrBarcodeJson.get("idOrBarcode").asText();
        return handoverService.addPackage(uuid, idOrBarcode);
    }

    @PutMapping("/{uuid}/confirm")
    Handover confirm(@PathVariable("uuid") UUID uuid) {
        return handoverService.confirm(uuid);
    }

    @PutMapping("/{uuid}/rollback")
    Handover rollback(
            @PathVariable("uuid") UUID uuid) {
        return handoverService.rollback(uuid);
    }
}
