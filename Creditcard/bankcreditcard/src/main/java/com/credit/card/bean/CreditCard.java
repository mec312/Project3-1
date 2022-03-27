package com.credit.card.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("creditCard")
@Data
public class CreditCard {
    @Id
    private String id;
    private String accountNumber;
    private Double creditLine;
    private Double balance;
    private String idClient;

}
