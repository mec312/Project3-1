package com.bootcamp.bankclient.service.Impl;

import com.bootcamp.bankclient.model.entities.ClientType;
import com.bootcamp.bankclient.repository.ClientTypeRepository;
import com.bootcamp.bankclient.service.ClientTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClientTypeServiceImpl implements ClientTypeService {

    @Autowired
    private ClientTypeRepository clientTypeRepository;
    @Override
    public Flux<ClientType> findAll() {
        return clientTypeRepository.findAll();
    }

    @Override
    public Mono<ClientType> findById(String id) {
        return clientTypeRepository.findById(id);
    }

    @Override
    public Mono<ClientType> findByCode(String code) {
        return clientTypeRepository.findByCode(code);
    }

    @Override
    public Mono<ClientType> create(ClientType customerType) {
        return clientTypeRepository.save(customerType);
    }

    @Override
    public Mono<Void> delete(ClientType customerType) {
        return clientTypeRepository.delete(customerType);
    }
}
