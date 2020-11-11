package de.oth.regensburg.projektstudium.backend.controller;

import de.oth.regensburg.projektstudium.backend.entity.Package;
import de.oth.regensburg.projektstudium.backend.entity.PackageStatus;
import de.oth.regensburg.projektstudium.backend.entity.Person;
import de.oth.regensburg.projektstudium.backend.exceptions.InvalidRequestException;
import de.oth.regensburg.projektstudium.backend.service.PackageService;
import de.oth.regensburg.projektstudium.backend.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PackageController {

    private static final Logger log = LoggerFactory.getLogger(PackageController.class);
    private final PackageService packageService;
    private final PersonService personService;

    public PackageController(PackageService packageService, PersonService personService) {
        this.packageService = packageService;
        this.personService = personService;
    }

    @GetMapping("/packages")
    List<Package> findAll() {
        return packageService.findAll();
    }

    @GetMapping("/packages/{idOrBarcode}")
    Package findOneByIdOrBarcode(@PathVariable("idOrBarcode") String idOrBarcode) {
        return packageService.findOneByIdOrBarcode(idOrBarcode);
    }

    @PostMapping("/packages")
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

        return new ResponseEntity<>(packageService.addOrUpdatePackage(newPackage), HttpStatus.CREATED);
    }

    @PutMapping("/packages/{idOrBarcode}/status")
    Package updateStatus(@PathVariable("idOrBarcode") String idOrBarcode,
                         @RequestBody Package newPackage) {
        if (newPackage == null || newPackage.getStatus() == null) {
            throw new InvalidRequestException();
        }

        final PackageStatus newStatus = newPackage.getStatus();
        final Package dbPackage = packageService.findOneByIdOrBarcode(idOrBarcode);

        dbPackage.setStatus(newStatus);
        return packageService.addOrUpdatePackage(dbPackage);
    }
}
