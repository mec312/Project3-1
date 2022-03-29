package com.bootcamp.bankaccount.controller;

import com.bootcamp.bankaccount.models.bean.Account;
import com.bootcamp.bankaccount.models.dto.AccountDto;
import com.bootcamp.bankaccount.service.AccountService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/accounts")
public class AccountController {

  private final AccountService accountService;

  @CircuitBreaker(name = "getAccountCB",
          fallbackMethod = "fallbackGetAccount")
  @TimeLimiter(name = "getAccountCB")
  @GetMapping
  public Flux<AccountDto> getAccount() {
    log.debug("Getting Accounts!");
    return accountService.getAccounts();
  }

  @GetMapping("/{id}")
  public Mono<AccountDto> getAccount(@PathVariable String id) {
    log.debug("Getting a account!");
    return accountService.getAccountById(id);
  }

  @GetMapping("/findByClientId/{clientId}")
  public Flux<Account> getAccountByClientId(@PathVariable String clientId) {
    log.info("Getting a ClientId!");
    return accountService.getAccountByClientId(clientId);
  }

  @CircuitBreaker(name = "saveAccountCB",
          fallbackMethod = "fallbackSaveAccount")
  @TimeLimiter(name = "saveAccountCB")
  @PostMapping()
  public Mono<AccountDto> saveAccount(
          @RequestBody AccountDto accountDtoMono) {
    log.debug("Saving accounts!");
    return accountService.saveAccount(accountDtoMono);

  }

  @PutMapping("/{id}")
  public Mono<AccountDto> updateAccount(
          @RequestBody Mono<AccountDto> accountDtoMono,
          @PathVariable String id) {
    log.debug("Updating accounts!");
    return accountService.updateAccount(accountDtoMono, id);
  }

  @DeleteMapping("/{id}")
  public Mono<Void> deleteAccount(@PathVariable String id) {
    log.debug("Deleting accounts!");
    return accountService.deleteAccount(id);
  }

  private Mono<AccountDto> fallbackSaveAccount(
          AccountDto accountDto, RuntimeException re) {
    log.debug("Respondiendo con fallbackSaveAccount");
    return Mono.just(new AccountDto());
  }

  private Flux<AccountDto> fallbackGetAccount(
          RuntimeException re) {
    log.debug("Respondiendo con fallbackGetAccount");
    return Flux.just(new AccountDto());
  }

  private Flux<AccountDto> fallbackGetAccountTime(
          RuntimeException re) {
    log.debug("Respondiendo con fallbackGetAccountTime");
    return Flux.just(new AccountDto());
  }
}
