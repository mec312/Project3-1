package com.bootcamp.bankwithdrawal.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("withdrawal")
@Data
public class Withdrawal {
    @Id
    private String id;
    private String balance;
    private String currency;
    private String idClient;
    private String withdrawalNumber;
    private String typeSavingAcc;
    private String typeCurrentAcc;
    private String typeTermAcc;

}
