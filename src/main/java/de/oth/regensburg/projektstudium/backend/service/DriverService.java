package de.oth.regensburg.projektstudium.backend.service;

import de.oth.regensburg.projektstudium.backend.entity.Driver;
import de.oth.regensburg.projektstudium.backend.entity.Location;

import java.util.List;

public interface DriverService {
    List<Driver> findAll();

    Driver findOneById(Long id);

    Driver addDriver(Driver newDriver);

    Driver updateDriver(Driver newDriver);

    Driver updateLocation(Long driverId, Location location);
}
