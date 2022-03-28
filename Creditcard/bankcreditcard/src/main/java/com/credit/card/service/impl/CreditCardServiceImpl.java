package com.credit.card.service.impl;

import com.credit.card.bean.CreditCard;
import com.credit.card.bean.CreditMovement;
import com.credit.card.dto.CreditCardDto;
import com.credit.card.dto.CreditMovementDto;
import com.credit.card.repository.CreditCardRepository;
import com.credit.card.repository.CreditMovementRepository;
import com.credit.card.service.CreditCardService;
import com.credit.card.util.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRepository creditCardRepository;
    private final CreditMovementRepository creditMovementRepository;

    public Flux<CreditCard> findAllCreditCard() {
        return creditCardRepository.findAll();
    }

    public Mono<CreditCard> findByIdCreditCard(String id) {
        return creditCardRepository.findById(id);
    }

    public Mono<CreditCard> saveCreditCard(CreditCard creditCardMono) {
        return creditCardRepository.save(creditCardMono);
    }

    public Mono<CreditCardDto> updateCreditCard(
            Mono<CreditCardDto> creditCardMono, String id) {
        return creditCardRepository.findById(id)
                .flatMap(p -> creditCardMono.map(AppUtils::dtoToEntity)
                        .doOnNext(e -> e.setId(id)))
                .flatMap(creditCardRepository::save)
                .map(AppUtils::entityToDto);
    }

    public Mono<Void> deleteByIdCreditCard(String id) {
        return creditCardRepository.deleteById(id);
    }

    public CreditCard findCreditCardByIdClient(String idClient) {
        return creditCardRepository.findCreditCardByIdClient(idClient);
    }


    public String seeBalance(String idCard) {
        return creditCardRepository
                .findCreditCardByIdClient(idCard)
                .getBalance().toString();
    }

    public Mono<CreditMovementDto> consumeCard(
            String idCard, Double consume) {
        return findByIdCreditCard(idCard).map(card -> {
            card.setBalance(card.getBalance()-consume);
            updateCreditCard(Mono.just(AppUtils.entityToDto(card)),
                    idCard);
            return CreditMovementDto.builder()
                    .typeMovement("Consume")
                    .creditLine(card.getBalance())
                    .amount(consume.toString())
                    .idCredit(idCard)
                    .balance(card.getBalance()).build();
        });
    }

    public Mono<CreditMovementDto> payCard(String idCard, Double pay) {
        return findByIdCreditCard(idCard).map(credit -> {
            credit.setBalance(credit.getBalance()+pay);
            updateCreditCard(Mono.just(AppUtils.entityToDto(credit)),
                    idCard);
            return CreditMovementDto.builder()
                    .typeMovement("Consume")
                    .creditLine(credit.getCreditLine())
                    .amount(pay.toString())
                    .idCredit(idCard)
                    .balance(credit.getBalance())
                    .build();
        });
    }

    public Flux<CreditMovement> findAllCreditMovement() {
        return creditMovementRepository.findAll();
    }


    public Flux<CreditMovement> findByIdCreditMovementByCard(
            String idCard) {
        return creditMovementRepository.findAll()
                .filter(credit -> idCard.equals(credit.getIdCredit()));
    }

    public Mono<CreditMovement> findAllCreditMovementById(
            String idMovement) {
        return creditMovementRepository.findAll().filter(credit ->
                idMovement.equals(credit.getId())).next();
    }
}
