package com.bootcamp.bankaccount.handlers;

import com.bootcamp.bankaccount.models.dto.AccountDto;
import com.bootcamp.bankaccount.models.dto.ClientCommand;
import com.bootcamp.bankaccount.service.AccountService;
import com.bootcamp.bankaccount.service.ClientService;
import com.bootcamp.bankaccount.service.CreditService;
import com.bootcamp.bankaccount.util.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@AllArgsConstructor
public class AccountHandler {

  private final AccountService accountService;
  private final CreditService creditService;
  private final ClientService clientService;

  public Mono<ServerResponse> findAll(ServerRequest request) {
    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
            .body(accountService.getAccounts(), AccountDto.class);
  }

  public Mono<ServerResponse> newSavingAccount(ServerRequest request) {
    return request.bodyToMono(AccountDto.class)
            .flatMap(accountCreate -> clientService.getClient(accountCreate.getClientIdNumber())
                    .flatMap(customer -> {
                      accountCreate.setClient(ClientCommand.builder()
                              .name(customer.getName()).code(
                                      customer.getClientType().getCode())
                              .clientIdNumber(customer.getClientIdNumber()).build());
                      accountCreate.setAccountType(Constants.SAVING_ACCOUNT);
                      accountCreate.setMaxLimitMovementPerMonth(
                              accountCreate.getMaxLimitMovementPerMonth());
                      accountCreate.setMovementPerMonth(0);
                      return accountService.saveAccount(accountCreate);
                    })
            )
            .flatMap(c -> ServerResponse
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(c))
            ).switchIfEmpty(ServerResponse.badRequest().build());
  }


  public Mono<ServerResponse> deleteSavingAccount(ServerRequest request) {
    String id = request.pathVariable("id");
    return accountService.getAccountById(id)
            .doOnNext(c -> log.info("deleteConsumption: consumptonId={}",
                    c.getId()))
            .flatMap(c -> accountService.deleteAccount(id)
                    .then(ServerResponse.noContent().build()))
            .switchIfEmpty(ServerResponse.notFound().build());
  }
}
