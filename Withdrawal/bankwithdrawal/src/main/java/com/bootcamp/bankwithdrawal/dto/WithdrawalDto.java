package com.bootcamp.bankwithdrawal.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WithdrawalDto {
    private String id;
    private String balance;
    private String currency;
    private String idClient;
    private String withdrawalNumber;
    private String typeSavingAcc;
    private String typeCurrentAcc;
    private String typeTermAcc;

}
