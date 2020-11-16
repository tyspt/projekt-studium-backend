package de.oth.regensburg.projektstudium.backend.service;

import de.oth.regensburg.projektstudium.backend.entity.Building;

import java.util.List;

public interface BuildingService {
    List<Building> findAll();

    Building findOneById(Long id);

    Building addBuilding(Building newBuilding);

    Building updateBuilding(Building newBuilding);

    Building deleteBuilding(Long buildingId);
}
