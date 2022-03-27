package com.bootcamp.bankdeposit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepositDto {
    private String id;
    private Double amount;
    private String currency;
    private String idClient;
    private String fromAccountId;
    private String fromAccountNumber;
    private String toAccountNumber;
    private String toAccountId;
    private String idDepositor;
    private String timestamp;

    public DepositDto(Double amount, String currency, String idClient, String fromAccountId, String fromAccountNumber, String toAccountNumber, String toAccountId, String idDepositor, String timestamp) {
        this.amount = amount;
        this.currency = currency;
        this.idClient = idClient;
        this.fromAccountId=fromAccountId;
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.idDepositor = idDepositor;
        this.timestamp = timestamp;
    }
}
