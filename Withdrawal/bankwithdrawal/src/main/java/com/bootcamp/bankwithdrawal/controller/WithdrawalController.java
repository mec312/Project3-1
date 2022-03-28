package com.bootcamp.bankwithdrawal.controller;

import com.bootcamp.bankwithdrawal.dto.WithdrawalDto;
import com.bootcamp.bankwithdrawal.service.WithdrawalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@Slf4j
@RestController
@RequestMapping(path = "/api/withdrawal")
public class WithdrawalController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WithdrawalController.class);

    @Autowired
    private WithdrawalService withdrawalService;

    @GetMapping
    public Flux<WithdrawalDto> getWithdrawal() {
        LOGGER.debug("Getting Withdrawals!");
        return withdrawalService.getWithdrawal();
    }

    @GetMapping("/{id}")
    public Mono<WithdrawalDto> getWithdrawal(@PathVariable String id) {
        LOGGER.debug("Getting a withdrawals!");
        return withdrawalService.getWithdrawalById(id);
    }

    @PostMapping
    public Mono<WithdrawalDto> saveWithdrawal(@RequestBody Mono<WithdrawalDto> withdrawalDtoMono) {
        LOGGER.debug("Saving clients!");
        return withdrawalService.saveWithdrawal(withdrawalDtoMono);
    }

    @PutMapping("/{id}")
    public Mono<WithdrawalDto> updateWithdrawal(@RequestBody Mono<WithdrawalDto> withdrawalDtoMono, @PathVariable String id) {
        LOGGER.debug("Updating withdrawals!");
        return withdrawalService.updateWithdrawal(withdrawalDtoMono, id);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteWithdrawal(@PathVariable String id) {
        LOGGER.debug("Deleting withdrawals!");
        return withdrawalService.deleteWithdrawal(id);
    }

}
