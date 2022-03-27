package com.credit.card.repository;

import com.credit.card.bean.CreditCard;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

@Configuration
public interface CreditCardRepository extends ReactiveMongoRepository<CreditCard, String> {
    CreditCard findCreditCardByIdClient(String idClient);
}
