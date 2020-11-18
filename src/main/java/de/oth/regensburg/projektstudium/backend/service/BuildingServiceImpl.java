package de.oth.regensburg.projektstudium.backend.service;

import de.oth.regensburg.projektstudium.backend.entity.Building;
import de.oth.regensburg.projektstudium.backend.exceptions.NotFoundException;
import de.oth.regensburg.projektstudium.backend.repository.BuildingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingServiceImpl implements BuildingService {

    private static final Logger log = LoggerFactory.getLogger(BuildingService.class);
    private final BuildingRepository repository;

    public BuildingServiceImpl(BuildingRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Building> findAll() {
        return repository.findAll();
    }

    @Override
    public Building findOneById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(Building.class));
    }

    @Override
    public Building createBuilding(Building newBuilding) {
        return repository.save(newBuilding);
    }

    @Override
    public Building updateBuilding(Building newBuilding) {
        return repository.save(newBuilding);
    }

    @Override
    public Building deleteBuilding(Long buildingId) {
        final Building building = this.findOneById(buildingId);
        repository.delete(building);
        return building;
    }
}
