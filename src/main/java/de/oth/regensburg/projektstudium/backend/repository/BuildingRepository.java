package de.oth.regensburg.projektstudium.backend.repository;

import de.oth.regensburg.projektstudium.backend.entity.Building;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BuildingRepository extends ReactiveMongoRepository<Building, String> {

    Flux<Building> findAllByValue(String value);
    Mono<Building> findFirstByOwner(Mono<String> owner);
}