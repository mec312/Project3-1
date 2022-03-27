package com.bootcamp.bankclient.service;

import com.bootcamp.bankclient.model.entities.Client;
import com.bootcamp.bankclient.model.dto.ClientDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ClientService {

    public Mono<Client> save(Client client);

    Flux<ClientDto>getClients();
    Mono<ClientDto> getClientById(String id);

    Mono<ClientDto> getClientByName(String name);

    Mono<ClientDto> getClientByClientIdNumber(String clientNumber);

    Mono<ClientDto> saveClients(Mono<ClientDto> clientDtoMono);

    Mono<ClientDto> updateClients(Mono<ClientDto> clientDtoMono, String id);

    Mono<Void> deleteClient(String id);
}
