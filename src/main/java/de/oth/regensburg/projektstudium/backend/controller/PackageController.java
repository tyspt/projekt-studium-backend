package de.oth.regensburg.projektstudium.backend.controller;

import de.oth.regensburg.projektstudium.backend.entity.Package;
import de.oth.regensburg.projektstudium.backend.exceptions.NotFoundException;
import de.oth.regensburg.projektstudium.backend.repository.PackageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PackageController {

    private static final Logger log = LoggerFactory.getLogger(PackageController.class);
    private final PackageRepository repository;

    public PackageController(PackageRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/packages")
    List<Package> findAll() {
        return repository.findAll();
    }

    @GetMapping("/packages/{idOrBarcode}")
    Package findOneByIdOrBarcode(@PathVariable("idOrBarcode") String idOrBarcode) {
        Long id = Long.valueOf(idOrBarcode);
        Optional<Package> resById = repository.findById(id);

        Package pkg = new Package();
        pkg.setBarcode(idOrBarcode);
        Optional<Package> resByBarcode = repository.findOne(Example.of(pkg));

        return resById
                .orElseGet(() -> resByBarcode
                        .orElseThrow(() -> new NotFoundException(Package.class)));
    }

    @PostMapping("/packages")
    Package addPackage(@RequestBody Package newPackage) {
        return repository.save(newPackage);
    }
}
