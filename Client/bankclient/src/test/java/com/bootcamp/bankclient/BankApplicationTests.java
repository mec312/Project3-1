package com.bootcamp.bankclient;

import com.bootcamp.bankclient.controller.ClientController;
import com.bootcamp.bankclient.model.dto.ClientDto;
import com.bootcamp.bankclient.model.entities.ClientType;
import com.bootcamp.bankclient.service.ClientService;
import com.bootcamp.bankclient.service.ClientTypeService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(ClientController.class)
class BankApplicationTests {

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private ClientService service;

    @MockBean
    private ClientTypeService clientTypeService;

    @Test
    public void addClientTest(){

        Mono<ClientDto> clientDtoMono=Mono.just(new ClientDto("4354534534543","pepe","7","7","ga@gmail.com","9433534543","las flores sjl", ClientType.builder().build()));
        when(service.saveClients(clientDtoMono)).thenReturn(clientDtoMono);

        webTestClient.post().uri("/api/clients/create")
                .body(Mono.just(clientDtoMono),ClientDto.class)
                    .exchange()
                .expectStatus().isOk();



    }

    /*@Test
    public void getClientsTest(){
        Flux<ClientDto> clientDtoFlux=Flux.just(new ClientDto("4354534534543","pepe","7","7","ga@gmail.com","9433534543","las flores sjl",ClientType.builder().build())
        ,new ClientDto("234324233","pep","6","6","ga7@gmail.com","945674543","las flores 22 sjl",ClientType.builder().build()));
        when(service.getClients()).thenReturn(clientDtoFlux);

        Flux<ClientDto> responseBody=webTestClient.get().uri("/api/clients/")
                .exchange()
                .expectStatus().isOk()
                .returnResult(ClientDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(new ClientDto("4354534534543","pepe","7","7","ga@gmail.com","9433534543","las flores sjl",ClientType.builder().build()))
                .expectNext(new ClientDto("234324233","pep","6","6","ga7@gmail.com","945674543","las flores 22 sjl",ClientType.builder().build()))
                .verifyComplete();


    }*/

}
