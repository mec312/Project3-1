package com.bootcamp.bankdeposit.service;

import com.bootcamp.bankdeposit.bean.Deposit;
import com.bootcamp.bankdeposit.dto.DepositDto;
import com.bootcamp.bankdeposit.repository.DepositRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class DepositServiceImplTest {

    @Mock
    private DepositRepository depositRepository;
    @InjectMocks
    private DepositServiceImpl service;

    private Flux<Deposit> fluxDto;

    private Mono<DepositDto> depositDtoMono;
    private Mono<DepositDto> depositeCreated;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        //fluxDto = Flux.just(new Deposit("098765432", 13100.00, "USD", "007", "", "876543222", "Pepe", "8765432", ""));

        //depositDtoMono = Mono.just(new DepositDto(13100.00, "USD", "007", "", "876543222", "Pepe", "8765432"));
    }

    @Test
    void getDeposit() {
        Mockito.when(depositRepository.findAll()).thenReturn(fluxDto);

        Assertions.assertNotNull(service.getDeposit());
    }

    @Test
    void saveDeposit() {
//        Mockito.when(depositRepository.save(ArgumentMatchers.any(depositDtoMono.getClass()))).thenReturn();

        //Assertions.assertNotNull(service.saveDeposit(depositDtoMono));
        //Mockito.verify(depositRepository, Mockito.times(1)).save(ArgumentMatchers.any(depositDtoMono.getClass())));
    }
}