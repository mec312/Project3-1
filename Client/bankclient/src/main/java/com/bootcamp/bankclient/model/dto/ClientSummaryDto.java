package com.bootcamp.bankclient.model.dto;

import com.bootcamp.bankclient.model.entities.Account;
import com.bootcamp.bankclient.model.entities.CreditCart;
import lombok.*;
import reactor.core.publisher.Flux;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientSummaryDto {
    private String name;
    private String typeClient;
    private String email;
    private List<CreditCart> creditCart;
    private List<Account> account;
}
