package com.bootcamp.bankclient.repository;

import com.bootcamp.bankclient.model.entities.ClientType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ClientTypeRepository extends ReactiveMongoRepository<ClientType, String> {


    Mono<ClientType> findByCode(String code);

}
