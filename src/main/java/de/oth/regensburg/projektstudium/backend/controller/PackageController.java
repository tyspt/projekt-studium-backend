package de.oth.regensburg.projektstudium.backend.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import de.oth.regensburg.projektstudium.backend.entity.Employee;
import de.oth.regensburg.projektstudium.backend.entity.Package;
import de.oth.regensburg.projektstudium.backend.entity.enums.PackageStatus;
import de.oth.regensburg.projektstudium.backend.exceptions.BadRequestException;
import de.oth.regensburg.projektstudium.backend.service.EmployeeService;
import de.oth.regensburg.projektstudium.backend.service.PackageService;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("packages")
public class PackageController {

    private static final Logger log = LoggerFactory.getLogger(PackageController.class);
    private final PackageService packageService;
    private final EmployeeService employeeService;

    public PackageController(PackageService packageService, EmployeeService employeeService) {
        this.packageService = packageService;
        this.employeeService = employeeService;
    }

    @GetMapping("")
    List<Package> findAll() {
        return packageService.findAll();
    }

    @GetMapping("/{idOrBarcode}")
    Package findOneByIdOrBarcode(@PathVariable("idOrBarcode") String idOrBarcode) {
        return packageService.findOneByIdOrBarcode(idOrBarcode);
    }

    @PostMapping("")
    ResponseEntity<Package> add(@RequestBody Package newPackage) {
        Employee recipient = newPackage.getRecipient();
        if (recipient.getId() == null) {
            recipient = employeeService.createEmployee(recipient);
            newPackage.setRecipient(recipient);
        }

        Employee sender = newPackage.getSender();
        if (sender.getId() == null) {
            sender = employeeService.createEmployee(sender);
            newPackage.setSender(sender);
        }

        return new ResponseEntity<>(packageService.createPackage(newPackage), HttpStatus.CREATED);
    }

    @PutMapping("/{idOrBarcode}")
    Package updateStatus(
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
            case REATTEMPT_DELIVERY:
                return packageService.rescheduleDelivery(idOrBarcode);
            case NOT_DELIVERABLE:
                return packageService.markNotDeliverable(idOrBarcode);
            case DELIVERED:
                return packageService.deliverPackage(idOrBarcode);
        }

        throw new RuntimeException("Not supported yet");
    }
}
