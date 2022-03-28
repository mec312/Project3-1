package com.bootcamp.bankdeposit.controller;

import com.bootcamp.bankdeposit.dto.DepositDto;
import com.bootcamp.bankdeposit.service.DepositService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping(path = "/api/deposit")
public class DepositController {

    @Resource
    private DepositService depositService;

    @CircuitBreaker(name = "getDepositCB", fallbackMethod = "fallbackGetDeposit")
    @TimeLimiter(name = "getDepositCB", fallbackMethod = "fallbackGetDeposit")
    @GetMapping
    public Flux<DepositDto> getDeposit() {
        log.debug("Getting Deposit!");
        return depositService.getDeposit();
    }

    @GetMapping("/{id}")
    public Mono<DepositDto> getDeposit(@PathVariable String id) {
        log.debug("Getting a deposit!");
        return depositService.getDepositById(id);
    }

    @PostMapping
    public Mono<DepositDto> saveDeposit(@RequestBody DepositDto depositDtoMono) {
        log.debug("Saving deposit!");
        return depositService.saveDeposit(depositDtoMono);
    }

    @PutMapping("/{id}")
    public Mono<DepositDto> updateDeposit(
            @RequestBody Mono<DepositDto> depositDtoMono,
            @PathVariable String id) {
        log.debug("Updating deposit!");
        return depositService.updateDeposit(depositDtoMono, id);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteDeposit(@PathVariable String id) {
        log.debug("Deleting deposit!");
        return depositService.deleteDeposit(id);
    }

}
