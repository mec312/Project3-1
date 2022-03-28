package com.bootcamp.bankwithdrawal.service;

import com.bootcamp.bankwithdrawal.dto.WithdrawalDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WithdrawalService {

    Flux<WithdrawalDto> getWithdrawal();
    Mono<WithdrawalDto> getWithdrawalById(String id);

    //Mono<WithdrawalDto> getWithdrawalByName(String name);

    Mono<WithdrawalDto> getWithdrawalByWithdrawalNumber(String withdrawalNumber);

    Mono<WithdrawalDto> saveWithdrawal(Mono<WithdrawalDto> withdrawalDtoMono);

    Mono<WithdrawalDto> updateWithdrawal(Mono<WithdrawalDto> withdrawalDtoMono, String id);

    Mono<Void> deleteWithdrawal(String id);
}
