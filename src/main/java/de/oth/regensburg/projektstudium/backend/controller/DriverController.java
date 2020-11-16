package de.oth.regensburg.projektstudium.backend.controller;

import de.oth.regensburg.projektstudium.backend.entity.Driver;
import de.oth.regensburg.projektstudium.backend.entity.Location;
import de.oth.regensburg.projektstudium.backend.exceptions.BadRequestException;
import de.oth.regensburg.projektstudium.backend.service.DriverService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("drivers")
public class DriverController {

    private static final Logger log = LoggerFactory.getLogger(DriverController.class);
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping("")
    List<Driver> findAll() {
        return driverService.findAll();
    }

    @GetMapping("/{id}")
    Driver findOneById(@PathVariable("id") Long id) {
        return driverService.findOneById(id);
    }

    @PostMapping("")
    Driver addDriver(@RequestBody Driver driver) {
        return driverService.addDriver(driver);
    }

    @PutMapping("/{id}")
    Driver updateDriver(@PathVariable("id") Long id, @RequestBody Driver driver) {
        driver.setId(id);
        return driverService.updateDriver(driver);
    }

    @DeleteMapping("/{id}")
    Driver deleteDriver(@PathVariable("id") Long id) {
        return driverService.deleteDriver(id);
    }

    @PutMapping("/location")
    Driver updateLocation(@RequestHeader("Driver-ID") String driverId,
                          @RequestBody Location location) {
        if (StringUtils.isEmpty(driverId) || !NumberUtils.isParsable(driverId)) {
            throw new BadRequestException("can't find valid driver id");
        }
        if (location == null || location.getLatitude() == null || location.getLongitude() == null) {
            throw new BadRequestException("invalid location");
        }
        return driverService.updateLocation(Long.parseLong(driverId), location);
    }
}
