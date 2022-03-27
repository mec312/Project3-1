package com.credit.card.util;

import com.credit.card.bean.CreditCard;
import com.credit.card.bean.CreditMovement;
import com.credit.card.dto.CreditCardDto;
import org.springframework.beans.BeanUtils;

public class AppUtils {

    public static CreditCardDto entityToDto(CreditCard creditCard){
        CreditCardDto accDto=new CreditCardDto();
        BeanUtils.copyProperties(creditCard,accDto);
        return accDto;
    }

    public static CreditCard dtoToEntity(CreditCardDto accDto){
        CreditCard deposit=new CreditCard();
        BeanUtils.copyProperties(accDto,deposit);
        return deposit;
    }



}
