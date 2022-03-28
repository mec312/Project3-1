package com.bootcamp.bankwithdrawal.util;

import com.bootcamp.bankwithdrawal.bean.Withdrawal;
import com.bootcamp.bankwithdrawal.dto.WithdrawalDto;
import org.springframework.beans.BeanUtils;

@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public class AppUtils {

    @SuppressWarnings({"checkstyle:WhitespaceAfter", "checkstyle:WhitespaceAround", "checkstyle:FinalParameters", "checkstyle:MissingJavadocMethod"})
    public static WithdrawalDto entityToDto(Withdrawal withdrawal){
        WithdrawalDto accDto=new WithdrawalDto();
        BeanUtils.copyProperties(withdrawal,accDto);
        return accDto;
    }

    @SuppressWarnings({"checkstyle:WhitespaceAfter", "checkstyle:WhitespaceAround", "checkstyle:FinalParameters", "checkstyle:MissingJavadocMethod"})
    public static Withdrawal dtoToEntity(WithdrawalDto accDto){
        Withdrawal withdrawal=new Withdrawal();
        BeanUtils.copyProperties(accDto,withdrawal);
        return withdrawal;
    }
}
