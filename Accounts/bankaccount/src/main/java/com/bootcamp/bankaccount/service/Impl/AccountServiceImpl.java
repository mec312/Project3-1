package com.bootcamp.bankaccount.service.Impl;

import com.bootcamp.bankaccount.models.bean.Account;
import com.bootcamp.bankaccount.models.dto.AccountDto;
import com.bootcamp.bankaccount.models.dto.ClientDto;
import com.bootcamp.bankaccount.models.dto.CreditCardDto;
import com.bootcamp.bankaccount.repository.AccountRepository;
import com.bootcamp.bankaccount.service.AccountService;
import com.bootcamp.bankaccount.util.AppUtils;
import com.bootcamp.bankaccount.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private RestTemplate restTemplate;

  @Value("${microservice-clients.uri}")
  private String urlClients;

  @Value("${microservice-creditcard.uri}")
  private String urlCreditCard;

  @Value("${apiclient.uri}")
  private String urlApigateway;

  public Flux<AccountDto> getAccounts() {
    return accountRepository.findAll().map(AppUtils::entityToDto);
  }

  @Override
  public Mono<AccountDto> getAccountById(String id) {
    return accountRepository.findById(id).map(AppUtils::entityToDto);
  }

  @Override
  public Mono<AccountDto> saveAccount(AccountDto accountDto) {
    if (StringUtils.equals(accountDto.getAccountType(), Constants.VIP)
            || StringUtils.equals(accountDto.getAccountType(), Constants.PYME)) {
      if (!Objects.isNull(restTemplate.getForObject(
              urlApigateway + urlCreditCard + accountDto.getAccountType(),
              CreditCardDto.class))) {
        if (accountDto.getBalance() >= accountDto.getMinimumOpeningAmount()) {
          return Mono.just(accountDto).map(AppUtils::dtoToEntity)
                  .flatMap(accountRepository::insert)
                  .map(AppUtils::entityToDto);
        } else {
          log.debug(Constants.MSJ_MONTO_MENOR_APERTURA);
          return null;
        }
      } else {
        log.debug(Constants.MSJ_REQUIERE_TC);
        return null;
      }

    } else {
      // NO ES CUENTA VIP, NO ES CUENTA PYME
      if (accountDto.getBalance() >= accountDto.getMinimumOpeningAmount()) {
        return Mono.just(accountDto).map(AppUtils::dtoToEntity)
                .flatMap(accountRepository::insert)
                .map(AppUtils::entityToDto);
      } else {
        log.debug(Constants.MSJ_MONTO_MENOR_APERTURA);
        return null;
      }
    }


  }

  private ClientDto obtainClient(String clientId) {
    ClientDto clientDto = restTemplate.getForObject(urlApigateway
            + urlClients + "findClientCredit/"
            + clientId, ClientDto.class);
    log.debug("clientDto:" + clientDto.getClientIdNumber());
    return clientDto;
  }

  @Override
  public Mono<AccountDto> updateAccount(Mono<AccountDto> AccountDtoMono, String id) {
    return accountRepository.findById(id)
            .flatMap(p -> AccountDtoMono.map(AppUtils::dtoToEntity)
                    .doOnNext(e -> e.setId(id)))
            .flatMap(accountRepository::save)
            .map(AppUtils::entityToDto);
  }

  public Mono<Void> deleteAccount(String id) {
    return accountRepository.deleteById(id);
  }

  @Override
  public Flux<Account> validateClientIdNumber(String customerIdentityNumber) {
    return accountRepository.findByClientIdNumber(customerIdentityNumber)
            .switchIfEmpty(Mono.just(Account.builder()
                    .clientIdNumber(null).build()));
  }

  @Override
  public Flux<Account> findByClientIdNumber(String clientIdNumber) {
    return accountRepository.findByClientIdNumber(clientIdNumber);
  }

  @Override
  public Mono<Account> findByAccountNumber(String accountNumber) {
    return accountRepository.findByAccountNumber(accountNumber);
  }

  @Override
  public Flux<Account> getAccountByClientId(String clientIdNumber){
    return accountRepository.findByClientIdNumber(clientIdNumber);
  }
}
