package com.bootcamp.bankclient.controller;

import com.bootcamp.bankclient.model.entities.Client;
import com.bootcamp.bankclient.model.dto.ClientDto;
import com.bootcamp.bankclient.service.ClientService;
import com.bootcamp.bankclient.service.ClientTypeService;
import com.bootcamp.bankclient.service.Impl.ClientServiceImpl;
import com.bootcamp.bankclient.service.Impl.ClientTypeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;


@RestController
@RequestMapping(path ="/api/clients")
public class ClientController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientTypeService typeService;

    @GetMapping()
    public Flux<ClientDto> getClients(){
        LOGGER.debug("Getting clients!");
        return clientService.getClients();
    }

    @GetMapping("/{id}")
    public Mono<ClientDto> getClient(@PathVariable String id){
        LOGGER.debug("Getting a client:{0}",id);
        return clientService.getClientById(id);
    }
    @GetMapping("/name/{name}")
    public Mono<ClientDto> getClientByName(@PathVariable String name){

        LOGGER.debug("Getting getClientByName!");
        return clientService.getClientByName(name);
    }
    @GetMapping("/findClientCredit/{clientIdNumber}")
    public Mono<ResponseEntity<ClientDto>> findClientCredit(@PathVariable String clientIdNumber){
        LOGGER.debug("findByClientIdNumber: clientIdNumber={0}", clientIdNumber);
        return clientService.getClientByClientIdNumber(clientIdNumber)
                .map(saveClient-> ResponseEntity.ok(saveClient))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public Mono<ClientDto> saveClient(@RequestBody Mono<ClientDto> clientDtoMono){
        return clientService.saveClients(clientDtoMono);
    }
    @PostMapping
    public Mono<ResponseEntity<Client>> create(@RequestParam String code, @RequestBody Mono<Client> request){
        return request
                .flatMap(clientCreate -> typeService.findByCode(code)
                        .flatMap(type ->{
                            if(clientCreate.getClientType() !=null && !code.equals(clientCreate.getClientType().getCode())) {
                                return Mono.empty();
                            } else{
                                clientCreate.setClientType(type);
                                return clientService.save(clientCreate);
                            }
                        }))
                .map(customerCreate -> ResponseEntity.created(URI.create("/api/clients/".concat(customerCreate.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(customerCreate))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatus.BAD_REQUEST)));
    }

    @PutMapping("/{id}")
    public Mono<ClientDto> updateClient(@RequestBody Mono<ClientDto> clientDtoMono,@PathVariable String id){
        return clientService.updateClients(clientDtoMono,id);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteClient(@PathVariable String id){
        return clientService.deleteClient(id);
    }

}
