package de.oth.regensburg.projektstudium.backend.controller;

import de.oth.regensburg.projektstudium.backend.entity.Package;
import de.oth.regensburg.projektstudium.backend.repository.PackageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PackageController {

    private final PackageRepository repository;
    private static final Logger log = LoggerFactory.getLogger(PackageController.class);

    public PackageController(PackageRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/packages")
    List<Package> all() {
        return repository.findAll();
    }
}
