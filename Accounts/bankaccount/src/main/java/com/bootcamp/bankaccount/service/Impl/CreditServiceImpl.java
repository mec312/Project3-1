package com.bootcamp.bankaccount.service.Impl;

import com.bootcamp.bankaccount.models.dto.Credit;
import com.bootcamp.bankaccount.service.CreditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;

import static com.bootcamp.bankaccount.util.Constants.API_CREDIT_URL;

@Service
@Slf4j
@AllArgsConstructor
public class CreditServiceImpl implements CreditService {

  private final WebClient creditWebClient;

  public CreditServiceImpl() {
    this.creditWebClient = WebClient.builder()
            .baseUrl(API_CREDIT_URL)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
  }

  @Override
  public Flux<Credit> getCredit(String clientIdNumber) {
    Map<String, Object> params = new HashMap<String, Object>();
    log.info("Initializing credit query");
    params.put("clientIdNumber", clientIdNumber);
    return creditWebClient.get()
            .uri("client/{clientIdNumber}", clientIdNumber)
            .accept(MediaType.APPLICATION_JSON)
            .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(Credit.class))
            .switchIfEmpty(Flux.just(Credit.builder()
                    .debitor(false).build()))
            .doOnNext(c -> log.info("Credit REsponse : Contract= {}", c.getContractNumber()));
  }

}
