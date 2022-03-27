package com.credit.card.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("creditMovement")
@Data
public class CreditMovement {

    @Id
    private String id;
    private String idCredit;
    private String typeMovement;
    private String amount;
    private Double creditLine;
    private Double balance;

    public static boolean isIdEqual(CreditMovement test, String idCard){
        if(test.getIdCredit() == idCard){
            return true;
        }else{
            return false;
        }
    }

}
