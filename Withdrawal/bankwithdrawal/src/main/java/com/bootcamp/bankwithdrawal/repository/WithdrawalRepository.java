package com.bootcamp.bankwithdrawal.repository;

import com.bootcamp.bankwithdrawal.bean.Withdrawal;
import com.bootcamp.bankwithdrawal.dto.WithdrawalDto;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

@Configuration
public interface WithdrawalRepository extends ReactiveMongoRepository<Withdrawal, String> {


    /*Mono<WithdrawalDto> findByName(String name);*/
    Mono<WithdrawalDto> findByWithdrawalNumber(String withdrawalNumber);
}
