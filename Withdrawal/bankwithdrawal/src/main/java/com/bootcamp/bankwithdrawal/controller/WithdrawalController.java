package com.bootcamp.bankwithdrawal.controller;

import com.bootcamp.bankwithdrawal.dto.WithdrawalDto;
import com.bootcamp.bankwithdrawal.service.WithdrawalService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(path = "/api/withdrawal")
public class WithdrawalController {

    @Autowired
    private WithdrawalService withdrawalService;

    @GetMapping
    public Flux<WithdrawalDto> getWithdrawal() {
        log.debug("Getting Withdrawals!");
        return withdrawalService.getWithdrawal();
    }

    @GetMapping("/{id}")
    public Mono<WithdrawalDto> getWithdrawal(@PathVariable String id) {
        log.debug("Getting a withdrawals!");
        return withdrawalService.getWithdrawalById(id);
    }

    @PostMapping
    public Mono<WithdrawalDto> saveWithdrawal(@RequestBody Mono<WithdrawalDto> withdrawalDtoMono) {
        log.debug("Saving clients!");
        return withdrawalService.saveWithdrawal(withdrawalDtoMono);
    }

    @PutMapping("/{id}")
    public Mono<WithdrawalDto> updateWithdrawal(@RequestBody Mono<WithdrawalDto> withdrawalDtoMono,
                                                @PathVariable String id) {
        log.debug("Updating withdrawals!");
        return withdrawalService.updateWithdrawal(withdrawalDtoMono, id);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteWithdrawal(@PathVariable String id) {
        log.debug("Deleting withdrawals!");
        return withdrawalService.deleteWithdrawal(id);
    }

}
