package com.bootcamp.bankclient.service;

import com.bootcamp.bankclient.model.entities.ClientType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientTypeService {

    Flux<ClientType> findAll();

    Mono<ClientType> findById(String id);

    Mono<ClientType> findByCode(String code);

    Mono<ClientType> create(ClientType customerType);


    Mono<Void> delete(ClientType customerType);

}
