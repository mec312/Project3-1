package com.credit.card.service;

import com.credit.card.bean.CreditCard;
import com.credit.card.bean.CreditMovement;
import com.credit.card.dto.CreditCardDto;
import com.credit.card.dto.CreditMovementDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditCardService {

    public Flux<CreditCard> findAllCreditCard();
    public Mono<CreditCard> findByIdCreditCard(String id);
    public Mono<CreditCard> saveCreditCard(CreditCard creditCard);
    public Mono<CreditCardDto> updateCreditCard(Mono<CreditCardDto> creditCardMono, String id);
    public Mono<Void> deleteByIdCreditCard(String id);
    public CreditCard findCreditCardByIdClient(String idClient);
    public String seeBalance(String idCard);
    public Mono<CreditMovementDto> consumeCard(String idCard, Double consume);
    public Mono<CreditMovementDto> payCard(String idCard, Double pay);
    public Flux<CreditMovement> findAllCreditMovement();
    public Flux<CreditMovement> findByIdCreditMovementByCard(String idCard);
    public Mono<CreditMovement> findAllCreditMovementById(String idMovement);



}
