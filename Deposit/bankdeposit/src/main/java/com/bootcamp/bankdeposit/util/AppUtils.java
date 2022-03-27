package com.bootcamp.bankdeposit.util;

import com.bootcamp.bankdeposit.bean.Deposit;
import com.bootcamp.bankdeposit.dto.DepositDto;
import org.springframework.beans.BeanUtils;

public class AppUtils {

    public static DepositDto entityToDto(Deposit deposit) {
        DepositDto accDto = new DepositDto();
        BeanUtils.copyProperties(deposit, accDto);
        return accDto;
    }

    public static Deposit dtoToEntity(DepositDto accDto) {
        Deposit deposit = new Deposit();
        BeanUtils.copyProperties(accDto, deposit);
        return deposit;
    }
}
