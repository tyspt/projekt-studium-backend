package de.oth.regensburg.projektstudium.backend.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import de.oth.regensburg.projektstudium.backend.entity.Package;
import de.oth.regensburg.projektstudium.backend.entity.Person;
import de.oth.regensburg.projektstudium.backend.entity.enums.PackageStatus;
import de.oth.regensburg.projektstudium.backend.exceptions.BadRequestException;
import de.oth.regensburg.projektstudium.backend.service.PackageService;
import de.oth.regensburg.projektstudium.backend.service.PersonService;
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
    private final PersonService personService;

    public PackageController(PackageService packageService, PersonService personService) {
        this.packageService = packageService;
        this.personService = personService;
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
        Person recipient = newPackage.getRecipient();
        if (recipient.getId() == null) {
            recipient = personService.addPerson(recipient);
            newPackage.setRecipient(recipient);
        }

        Person sender = newPackage.getSender();
        if (sender.getId() == null) {
            sender = personService.addPerson(sender);
            newPackage.setSender(sender);
        }

        return new ResponseEntity<>(packageService.addPackage(newPackage), HttpStatus.CREATED);
    }

    @PutMapping("")
    Package updateStatus(@PathVariable("idOrBarcode") String idOrBarcode,
                         @RequestBody ObjectNode statusJson) {
        if (StringUtils.isEmpty(idOrBarcode) || statusJson == null || !statusJson.has("status")) {
            throw new BadRequestException();
        }

        final PackageStatus status;
        try {
            status = PackageStatus.valueOf(statusJson.get("status").asText());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("status value can not be recognized by server");
        }

        return packageService.updateStatus(idOrBarcode, status);
    }
}
