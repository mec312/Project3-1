package com.bootcamp.bankdeposit.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 */
@Document("account")
@Data
public class Account {
    @Id
    private String id;
    private Double balance;
    private String currency;
    private String idClient;
    private String accountNumber;

    private String canBeDeposit;
    /**
     * maxLimitMovementPerMonth: indica la cantidad maxima de veces que se puede depositar
     * X: cantidad maxima de despositos mensuales
     * 0: no se puede depositar
     */
    private int maxLimitMovementPerMonth;
    /**
     * accountType: indica  el tipo de cuenta
     * 1: Cuenta de Ahorro
     * 2: Cuenta Corriente
     * 3: Plazo fijo
     */
    private String accountType;
    /**
     * limitCounter: acumula la ncatidad de depositos que se van realizando
     */
    private int movementPerMonth;

    /**
     *minimumOpeningAmount: monto mínimo de apertura que puede ser 0.
     */

    private Double minimumOpeningAmount;


    /**
     *minimumDailyAverageAmountEachMonth: monto mínimo de promedio diario cada mes
     */

    private Double minimumDailyAverageAmountEachMonth;

    private int maxLimitTransaction;


}
