package com.bootcamp.bankclient.controller;

import com.bootcamp.bankclient.model.entities.ClientType;
import com.bootcamp.bankclient.service.Impl.ClientTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("api/clients/type")
public class ClientTypeController {
    @Autowired
    private ClientTypeServiceImpl service;

    @GetMapping
    public Flux<ClientType> getAllTypes(){
        return service.findAll();
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id){
        return service.findById(id)
                .flatMap(type->{
                    return service.delete(type);
                })
                .map(type -> ResponseEntity.noContent().build());
    }

    @PostMapping
    public Mono<ResponseEntity<ClientType>> create(@RequestBody ClientType type){
        return service.create(type)
                .map(typeCreate ->{
                    return ResponseEntity.created(URI.create("/api/clients/type"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(typeCreate);
                });
    }
}
