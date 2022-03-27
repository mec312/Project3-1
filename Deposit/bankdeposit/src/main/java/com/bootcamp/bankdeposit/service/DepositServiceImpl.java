package com.bootcamp.bankdeposit.service;

import com.bootcamp.bankdeposit.dto.AccountDto;
import com.bootcamp.bankdeposit.dto.DepositDto;
import com.bootcamp.bankdeposit.repository.DepositRepository;
import com.bootcamp.bankdeposit.util.AppUtils;
import com.bootcamp.bankdeposit.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class DepositServiceImpl implements DepositService {

    @Value("${microservice-accounts.uri}")
    private String urlAccounts;
    @Value("${apiclient.uri}")
    private String urlApigateway;


    private static final Logger LOGGER = LoggerFactory.getLogger(DepositServiceImpl.class);
    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    private WebClient.Builder webClient;
    @Autowired
    private RestTemplate restTemplate;

    public Flux<DepositDto> getDeposit() {

        LOGGER.debug("In getDeposit()");
        return depositRepository.findAll().map(AppUtils::entityToDto);
    }

    @Override
    public Mono<DepositDto> getDepositById(String id) {
        return depositRepository.findById(id).map(AppUtils::entityToDto);
    }

    @Override
    public Mono<AccountDto> doTransfer(DepositDto depositDto) {
        AccountDto accountOutgoing = restTemplate.getForObject(urlApigateway + urlAccounts + depositDto.getFromAccountId(), AccountDto.class);
        AccountDto accountDestination = restTemplate.getForObject(urlApigateway + urlAccounts + depositDto.getToAccountId(), AccountDto.class);
        if(accountOutgoing.getBalance()>=depositDto.getAmount()&&accountOutgoing.getCurrency()==accountDestination.getCurrency()){
            accountOutgoing.setBalance(accountDestination.getBalance()-depositDto.getAmount());
            accountDestination.setBalance(accountDestination.getBalance()+depositDto.getAmount());
                return Mono.just(accountDestination);
        }else{
            return null;
        }

    }

/*
    @Override
    public Mono<DepositDto> getDepositByName(String name) {
        return depositRepository.findByName(name);
    }*/
/*
    @Override
    public Mono<DepositDto> getDepositByDepositNumber(String depositNumber) {
        return depositRepository.findByDepositNumber(depositNumber)
                .switchIfEmpty(Mono.just(DepositDto.builder()
                        .depositNumber(null).build()));
    }*/

    public Mono<DepositDto> saveDeposit(DepositDto depositDto) {
//    public Mono<DepositDto> saveDeposit(Mono<DepositDto> depositDto) {
        LOGGER.debug("url a invocar:" + urlApigateway + urlAccounts);
        /*depositDtoMono.subscribe(p ->
                 webClient.build().get().uri(urlApigateway+urlAccounts,p.getToAccountId())
                .retrieve()
                .bodyToMono(AccountDto.class));*/
        /*Mono<AccountDto> monoDto = webClient.build().get().uri(urlApigateway+urlAccounts,depositDtoMono.getToAccountId())
                .retrieve()
                .bodyToMono(AccountDto.class);*/
//        AccountDto dto = ((AccountDto) monoDto.block());
/*
        return Mono.just(depositDtoMono)
                .flatMap(
                        depo->{
                            Mono<AccountDto> account = webClient.build().get().uri(urlApigateway+urlAccounts,depo.getToAccountId())
                                    .retrieve()
                                    .bodyToMono(AccountDto.class);
                            //account.setBalance("123456");
                            //return Mono.just(depo);
                            return Mono.just(depositDtoMono).map(AppUtils::dtoToEntity)
                                    .flatMap(depositRepository::insert)
                                    .map(AppUtils::entityToDto);
                        }
                );*/
        /*Mono<AccountDto> monoDto = webClient.build().get().uri(urlApigateway+urlAccounts,depositDtoMono.getToAccountId())
                .exchangeToMono(response -> {
                    LOGGER.debug("--response-->" + response.statusCode());
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        Mono<AccountDto> dto = response.bodyToMono(AccountDto.class);

                        dto.subscribe(p -> LOGGER.debug("---->" + p.getAccountNumber()));
                        return dto;
                    }
                    else {
                        return response.createException().flatMap(Mono::error);
                    }
                });
*/

        try {
            AccountDto account = obtainAccountToDeposit(depositDto);

            if (approveDeposit(account, depositDto)) {
                LOGGER.debug("calculateBalance:");
                calculateBalance(account, depositDto);
                LOGGER.debug("updateBalanceAccount:");
                updateBalanceAccount(account);
                LOGGER.debug("savingDeposit:");
                return savingDeposit(depositDto);
            } else {
                throw new Exception("Error: Deposito no permitido");
            }
        } catch (Exception e) {
            LOGGER.error("TransactionError", e);
            //rolback transaction
            DepositDto dep = new DepositDto();
            dep.setIdDepositor("Deposito no permitido");
            return Mono.just(dep);
        }
    }

    private AccountDto obtainAccountToDeposit(DepositDto depositDto) {
        AccountDto account = restTemplate.getForObject(urlApigateway + urlAccounts + depositDto.getToAccountId(), AccountDto.class);
        LOGGER.debug("restTemplate:" + account.getAccountNumber());
        return account;
    }

    private Mono<DepositDto> savingDeposit(DepositDto depositDto) {
        LOGGER.debug("Service.savingDeposit");
        return Mono.just(depositDto).map(AppUtils::dtoToEntity)
                .flatMap(depositRepository::insert)
                .map(AppUtils::entityToDto);
    }

    private void updateBalanceAccount(AccountDto account) {
        account.setMovementPerMonth(account.getMovementPerMonth() + 1);
        restTemplate.put(urlApigateway + urlAccounts + account.getId(), account);
    }

    /**
     * Pasivos (cuentas bancarias)
     * -Ahorro:
     * libre  de  comisión  por  mantenimiento  y  con  un  límite máximo de movimientos mensuales.
     * -Cuenta  corriente:  posee  comisión  de mantenimiento y  sin  límite de movimientos mensuales.
     * -Plazo  fijo:  libre  de  comisión  por  mantenimiento, solo  permite  un movimiento de
     * retiro o depósito en un día específico del mes.
     */
    private boolean approveDeposit(AccountDto account, DepositDto depositDto) {
        boolean resp = false;
        if (Constant.TIPO_CUENTA_PLAZO.equalsIgnoreCase(account.getAccountType())) {
            if (Constant.CAN_BE_DEPOSIT.equalsIgnoreCase(account.getCanBeDeposit())) {

                resp = true;
            }
        } else if (Constant.TIPO_CUENTA_AHORRO.equalsIgnoreCase(account.getAccountType())) {
            if (account.getMovementPerMonth() <= account.getMaxLimitMovementPerMonth()) {
                resp = true;
            }
        } else if (Constant.TIPO_CUENTA_CORRIENTE.equalsIgnoreCase(account.getAccountType())) {
            resp = true;
        }
        return resp;
    }

    private void calculateBalance(AccountDto account, DepositDto depositDto) throws NumberFormatException {

        BigDecimal balance = BigDecimal.valueOf(account.getBalance());
        BigDecimal amount = BigDecimal.valueOf(depositDto.getAmount());
        BigDecimal newBalance = balance.add(amount);

        account.setBalance(newBalance.doubleValue());

    }

    public Mono<DepositDto> updateDeposit(Mono<DepositDto> DepositDtoMono, String id) {
        return depositRepository.findById(id)
                .flatMap(p -> DepositDtoMono.map(AppUtils::dtoToEntity)
                        .doOnNext(e -> e.setId(id)))
                .flatMap(depositRepository::save)
                .map(AppUtils::entityToDto);
    }

    public Mono<Void> deleteDeposit(String id) {
        return depositRepository.deleteById(id);
    }
}
