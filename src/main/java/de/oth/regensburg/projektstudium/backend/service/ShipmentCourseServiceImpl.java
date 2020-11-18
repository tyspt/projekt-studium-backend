package de.oth.regensburg.projektstudium.backend.service;

import de.oth.regensburg.projektstudium.backend.entity.ShipmentCourse;
import de.oth.regensburg.projektstudium.backend.repository.ShipmentCourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ShipmentCourseServiceImpl implements ShipmentCourseService {

    private static final Logger log = LoggerFactory.getLogger(ShipmentCourseService.class);

    private final ShipmentCourseRepository shipmentCourseRepository;

    public ShipmentCourseServiceImpl(ShipmentCourseRepository shipmentCourseRepository) {
        this.shipmentCourseRepository = shipmentCourseRepository;
    }

    @Override
    public ShipmentCourse createShipmentCourse(String message) {
        final ShipmentCourse shipmentCourse = new ShipmentCourse(message);
        return shipmentCourseRepository.save(shipmentCourse);
    }
}
