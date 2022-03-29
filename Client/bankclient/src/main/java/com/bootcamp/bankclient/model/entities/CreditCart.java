package com.bootcamp.bankclient.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreditCart {
    private String id;
    private String accountNumber;
    private Double creditLine;
    private Double balance;
    private String idClient;
}
