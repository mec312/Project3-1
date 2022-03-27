package com.bootcamp.bankaccount.service.Impl;

import com.bootcamp.bankaccount.models.dto.ClientQuery;
import com.bootcamp.bankaccount.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

  private final WebClient webClient;

  @Override
  public Mono<ClientQuery> getClient(String clientIdNumber) {
    return webClient.get()
            .uri("/findClientCredit/" + clientIdNumber)
            .retrieve()
            .bodyToMono(ClientQuery.class)
            .doOnNext(c -> log.info("Client response : {}", c.getName()));
  }
}
