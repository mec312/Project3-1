package com.credit.card.repository;

import com.credit.card.bean.CreditMovement;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

@Configuration
public interface CreditMovementRepository extends ReactiveMongoRepository<CreditMovement, String> {
}
