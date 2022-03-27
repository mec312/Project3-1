package com.credit.card.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import reactor.core.publisher.Mono;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonDeserialize
public class CreditCardDto {

    private String id;
    private String accountNumber;
    private Double creditLine;
    private Double balance;
    private String idClient;
}
