package de.oth.regensburg.projektstudium.backend.controller;

import de.oth.regensburg.projektstudium.backend.entity.Building;
import de.oth.regensburg.projektstudium.backend.service.BuildingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("buildings")
public class BuildingController {

    private static final Logger log = LoggerFactory.getLogger(BuildingController.class);
    private final BuildingService buildingService;

    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @GetMapping("")
    List<Building> findAll() {
        return buildingService.findAll();
    }

    @GetMapping("/{id}")
    Building findOneById(@PathVariable("id") Long id) {
        return buildingService.findOneById(id);
    }

    @PostMapping("")
    Building addBuilding(@RequestBody Building building) {
        return buildingService.addBuilding(building);
    }

    @PutMapping("/{id}")
    Building updateBuilding(@PathVariable("id") Long id, @RequestBody Building building) {
        building.setId(id);
        return buildingService.updateBuilding(building);
    }

    @DeleteMapping("/{id}")
    Building deleteBuilding(@PathVariable("id") Long id) {
        return buildingService.deleteBuilding(id);
    }
}
