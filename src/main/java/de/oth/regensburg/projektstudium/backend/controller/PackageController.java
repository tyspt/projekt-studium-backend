package de.oth.regensburg.projektstudium.backend.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import de.oth.regensburg.projektstudium.backend.dto.PackageDTO;
import de.oth.regensburg.projektstudium.backend.entity.enums.PackageStatus;
import de.oth.regensburg.projektstudium.backend.exceptions.BadRequestException;
import de.oth.regensburg.projektstudium.backend.service.PackageService;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("packages")
public class PackageController {

    private static final Logger log = LoggerFactory.getLogger(PackageController.class);
    private final PackageService packageService;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @GetMapping("")
    List<PackageDTO> findAll() {
        return packageService.findAll();
    }

    @GetMapping("/{idOrBarcode}")
    PackageDTO findOneByIdOrBarcode(@PathVariable("idOrBarcode") String idOrBarcode) {
        return packageService.findPackageDTOByIdOrBarcode(idOrBarcode);
    }

    @PostMapping("")
    ResponseEntity<PackageDTO> add(@RequestBody PackageDTO newPackage) {
        if (StringUtils.isEmpty(newPackage.getRecipientId()) || StringUtils.isEmpty((newPackage.getSenderId()))) {
            throw new BadRequestException("recipient id or sender id is invalid");
        }
        try {
            PackageDTO result = packageService.addPackage(newPackage);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (ParseException e) {
            throw new BadRequestException("Can not parse request body");
        }
    }

    @PutMapping("/{idOrBarcode}")
    PackageDTO updateStatus(
            @RequestHeader("Driver-ID") String driverId,
            @PathVariable("idOrBarcode") String idOrBarcode,
            @RequestBody ObjectNode statusJson) {
        if (StringUtils.isEmpty(idOrBarcode) || statusJson == null || !statusJson.has("status")) {
            throw new BadRequestException();
        }

        final PackageStatus status;
        try {
            status = PackageStatus.valueOf(statusJson.get("status").asText().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("status value can not be recognized by server");
        }

        switch (status) {
            case IN_TRANSPORT:
                if (!NumberUtils.isParsable(driverId)) {
                    throw new BadRequestException("can't find valid driver id");
                }
                return packageService.collectPackage(idOrBarcode, Long.parseLong(driverId));
        }

        throw new RuntimeException("Not supported yet");
    }
}
