package de.oth.regensburg.projektstudium.backend.service;

import de.oth.regensburg.projektstudium.backend.entity.Driver;
import de.oth.regensburg.projektstudium.backend.entity.Location;
import de.oth.regensburg.projektstudium.backend.exceptions.NotFoundException;
import de.oth.regensburg.projektstudium.backend.repository.DriverRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

    private static final Logger log = LoggerFactory.getLogger(DriverServiceImpl.class);
    private final DriverRepository repository;

    public DriverServiceImpl(DriverRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Driver> findAll() {
        return repository.findAll();
    }

    @Override
    public Driver findOneById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(Driver.class));
    }

    @Override
    public Driver addDriver(Driver newDriver) {
        return repository.save(newDriver);
    }

    @Override
    public Driver updateDriver(Driver newDriver) {
        return repository.save(newDriver);
    }

    @Override
    public Driver updateLocation(Long driverId, Location location) {
        final Driver dbDriver = this.findOneById(driverId);
        location.setLastUpdatedTimestamp(LocalDateTime.now());
        dbDriver.setLocation(location);
        return repository.save(dbDriver);
    }
}
