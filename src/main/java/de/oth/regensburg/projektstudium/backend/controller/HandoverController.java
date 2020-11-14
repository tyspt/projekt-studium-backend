package de.oth.regensburg.projektstudium.backend.controller;

import de.oth.regensburg.projektstudium.backend.entity.Handover;
import de.oth.regensburg.projektstudium.backend.exceptions.InvalidRequestException;
import de.oth.regensburg.projektstudium.backend.service.HandoverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/")
    List<Handover> findAll() {
        return handoverService.findAll();
    }

    @GetMapping("/{uuid}")
    Handover findOneByUuid(@PathVariable("uuid") UUID uuid) {
        return handoverService.findOneByUuid(uuid);
    }

    @PostMapping("/")
    ResponseEntity<Handover> addHandover(@RequestBody Handover newHandover) {
        if (newHandover == null || newHandover.getUuid() == null) {
            throw new InvalidRequestException();
        }
        return new ResponseEntity<>(handoverService.addHandover(newHandover), HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}/packages/{idOrBarcode}")
    Handover addPackage(
            @PathVariable("uuid") UUID uuid,
            @PathVariable("idOrBarcode") String idOrBarcode) {
        return handoverService.addPackage(uuid, idOrBarcode);
    }

    @PutMapping("/{uuid}/confirm")
    Handover confirm(
            @PathVariable("uuid") UUID uuid) {
        return handoverService.confirm(uuid);
    }

    @PutMapping("/{uuid}/rollback")
    Handover rollback(
            @PathVariable("uuid") UUID uuid) {
        return handoverService.rollback(uuid);
    }
}
