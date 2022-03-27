package com.credit.card.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditMovementDto {

    private String id;
    private String idCredit;
    private String typeMovement;
    private String amount;
    private Double creditLine;
    private Double balance;


}
