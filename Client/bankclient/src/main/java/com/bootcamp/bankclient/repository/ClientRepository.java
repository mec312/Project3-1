package com.bootcamp.bankclient.repository;
import com.bootcamp.bankclient.model.entities.Client;
import com.bootcamp.bankclient.model.dto.ClientDto;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

@Configuration
public interface ClientRepository extends ReactiveMongoRepository<Client, String> {

    Mono<ClientDto> findByName(String name);
    Mono<ClientDto> findByClientIdNumber(String clientIdNumber);
}
