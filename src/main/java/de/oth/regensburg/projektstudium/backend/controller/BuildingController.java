package de.oth.regensburg.projektstudium.backend.controller;

import de.oth.regensburg.projektstudium.backend.entity.Building;
import de.oth.regensburg.projektstudium.backend.repository.BuildingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BuildingController {

    private static final Logger log = LoggerFactory.getLogger(BuildingController.class);
    private final BuildingRepository repository;

    public BuildingController(BuildingRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/buildings")
    List<Building> all() {
        return repository.findAll();
    }
}
