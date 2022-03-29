package com.bootcamp.bankclient.model.entities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    private String id;
    private String accountNumber;
    private double balance;
    private String currency;
    private String accountType;
    private String canBeDeposit;
}
