package com.bootcamp.bankdeposit.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
@JsonDeserialize
public class AccountDto {
    private String id;
    private Double balance;

    private String accountNumber;
    private String accountType;

    private String currency;
    private String canBeDeposit;

    private int maxLimitMovementPerMonth;
    private int movementPerMonth;

    private Double minimumOpeningAmount;

    private Double minimumDailyAverageAmountEachMonth;


    private int maxLimitTransaction;


}
