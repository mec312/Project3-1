package com.credit.card.service;

import com.credit.card.bean.CreditCard;
import com.credit.card.bean.CreditMovement;
import com.credit.card.dto.CreditCardDto;
import com.credit.card.dto.CreditMovementDto;
import com.credit.card.repository.CreditCardRepository;
import com.credit.card.repository.CreditMovementRepository;
import com.credit.card.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreditCardServiceImpl implements CreditCardService{

    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private CreditMovementRepository creditMovementRepository;

    public Flux<CreditCard> findAllCreditCard() {
        return creditCardRepository.findAll();
    }

    public Mono<CreditCard> findByIdCreditCard(String id) {
        return creditCardRepository.findById(id);
    }

    public Mono<CreditCard> saveCreditCard(CreditCard creditCardMono) {
        return creditCardRepository.save(creditCardMono);
    }

    public Mono<CreditCardDto> updateCreditCard(Mono<CreditCardDto> creditCardMono, String id){
        return creditCardRepository.findById(id)
                .flatMap(p -> creditCardMono.map(AppUtils::dtoToEntity)
                        .doOnNext(e -> e.setId(id)))
                .flatMap(creditCardRepository::save)
                .map(AppUtils::entityToDto);
    }

    public Mono<Void> deleteByIdCreditCard(String id) {
        return creditCardRepository.deleteById(id);
    }

    public CreditCard findCreditCardByIdClient(String idClient){
        return creditCardRepository.findCreditCardByIdClient(idClient);
    }


    public String seeBalance(String idCard){
        Mono<CreditCard> card = creditCardRepository.findById(idCard);
        List<CreditCard> element = ((List<CreditCard>) card);
        return element.get(0).getBalance().toString();
    }

    public Mono<CreditMovementDto> consumeCard(String idCard, Double consume){
        Mono<CreditCard> creditCardMono = findByIdCreditCard(idCard);
        List<CreditCard> element = ((List<CreditCard>) creditCardMono);
        Double actualBalance = element.get(0).getBalance();
        element.get(0).setBalance(actualBalance-consume);
        CreditCardDto updateCard = AppUtils.entityToDto(element.get(0));

        List<CreditCardDto> updateCard3 = new ArrayList<>();
        updateCard3.add(updateCard);
        Mono<CreditCardDto> updateCard2 = ((Mono<CreditCardDto>) updateCard3);
        updateCreditCard(updateCard2, idCard);
        CreditMovementDto movement = new CreditMovementDto();
        movement.setTypeMovement("Consume");
        movement.setCreditLine(element.get(0).getCreditLine());
        movement.setAmount(consume.toString());
        movement.setIdCredit(idCard);
        movement.setBalance(actualBalance-consume);

        Mono<CreditMovementDto> consumeHistory = Mono.just(movement);
        return consumeHistory;

    }

    public Mono<CreditMovementDto> payCard(String idCard, Double pay){
        Mono<CreditCard> creditCardMono = findByIdCreditCard(idCard);
        List<CreditCard> element = ((List<CreditCard>) creditCardMono);
        Double actualBalance = element.get(0).getBalance();
        element.get(0).setBalance(actualBalance+pay);
        CreditCardDto updateCard = AppUtils.entityToDto(element.get(0));

        List<CreditCardDto> updateCard3 = new ArrayList<>();
        updateCard3.add(updateCard);
        Mono<CreditCardDto> updateCard2 = ((Mono<CreditCardDto>) updateCard3);
        updateCreditCard(updateCard2, idCard);
        CreditMovementDto movement = new CreditMovementDto();
        movement.setTypeMovement("Consume");
        movement.setCreditLine(element.get(0).getCreditLine());
        movement.setAmount(pay.toString());
        movement.setIdCredit(idCard);
        movement.setBalance(actualBalance+pay);

        Mono<CreditMovementDto> consumeHistory = Mono.just(movement);
        return consumeHistory;

    }

    public Flux<CreditMovement> findAllCreditMovement(){
        return creditMovementRepository.findAll();
    }


    public Flux<CreditMovement> findByIdCreditMovementByCard(String idCard){
        Flux<CreditMovement> allMovements = creditMovementRepository.findAll();
        List<CreditMovement> movementsList = ((List<CreditMovement>) allMovements);
        List<CreditMovement> movementsByCard = new ArrayList<>();
        for (int i=0; i< movementsList.size(); i++){
            if(movementsList.get(i).getIdCredit()==idCard ){
                movementsByCard.add(movementsList.get(0));
            }
        }
        Flux<CreditMovement> creditMovementFlux = (Flux<CreditMovement>) movementsByCard;
        return creditMovementFlux;
    }

    public Mono<CreditMovement> findAllCreditMovementById(String idMovement){
        Flux<CreditMovement> allMovements = creditMovementRepository.findAll();
        List<CreditMovement> movementsList = ((List<CreditMovement>) allMovements);
        CreditMovement movementById = new CreditMovement();
        for (int i=0; i< movementsList.size(); i++){
            if(movementsList.get(i).getId()==idMovement ){
                movementById = movementsList.get(0);
            }
        }
        Mono<CreditMovement> creditMovementFlux = Mono.just(movementById);
        return creditMovementFlux;
    }
}
