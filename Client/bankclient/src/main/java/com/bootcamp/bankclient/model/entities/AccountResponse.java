package com.bootcamp.bankclient.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import reactor.core.publisher.Flux;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AccountResponse {
    private Flux<Account> accounts;
}
