package de.oth.regensburg.projektstudium.backend.service;

import de.oth.regensburg.projektstudium.backend.entity.Driver;

import java.util.List;

public interface DriverService {
    List<Driver> findAll();

    Driver findOneById(Long id);

    Driver addDriver(Driver newDriver);

    Driver updateDriver(Driver newDriver);
}
