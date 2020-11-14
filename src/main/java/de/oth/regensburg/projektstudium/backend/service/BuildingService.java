package de.oth.regensburg.projektstudium.backend.service;

import de.oth.regensburg.projektstudium.backend.entity.Building;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BuildingService {
    Flux<Building> findAll();

    Mono<Building> findOneById(String id);
}
