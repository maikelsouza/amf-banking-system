package com.amf.banking.system.service;

import com.amf.banking.system.dto.AccountRequestDto;
import com.amf.banking.system.dto.AccountResponseDto;
import com.amf.banking.system.enums.AccountType;
import com.amf.banking.system.exception.BusinessException;
import com.amf.banking.system.exception.NotFoundException;
import com.amf.banking.system.model.Account;
import com.amf.banking.system.repository.AccountRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;


import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {


    @InjectMocks
    private AccountService service;

    @Mock
    private AccountRepository repository;

    @Mock
    private ClientService clientService;

    @Mock
    private TransactionService transactionService;

    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        service = new AccountService(repository, modelMapper, clientService, transactionService);
    }


    @Test
    public void shouldCreatedAnAccount(){

        String clientId = "68e18f594ebc588953f59094";

        Account account = new Account();
        account.setBalance(BigDecimal.ZERO);
        account.setClientId(clientId);
        account.setAccountNumber("123");
        when(clientService.existsById(clientId)).thenReturn(true);
        when(repository.existsByAccountNumber(Mockito.any())).thenReturn(false);
        when(repository.save(Mockito.any())).thenReturn(account);

        AccountRequestDto accountRequestDto = AccountRequestDto.builder()
                .type(AccountType.valueOf(AccountType.POUPANCA.name()))
                .clientId(clientId)
                .build();

        AccountResponseDto accountResponseDto = service.create(accountRequestDto);

        assertNotNull(accountResponseDto);
        Assertions.assertThat(accountResponseDto.getClientId()).isEqualTo(clientId);
        Assertions.assertThat(accountResponseDto.getAccountNumber()).isEqualTo("123");


        verify(repository).save(any(Account.class));
        verify(clientService).existsById(clientId);
    }

    @Test
    public void givenAnAccountWhenNotExistAClientShouldNotFoundException(){

        String clientId = "68e18f594ebc588953f59094";
        when(clientService.existsById(clientId)).thenReturn(false);

        AccountRequestDto accountRequestDto = AccountRequestDto.builder()
                .type(AccountType.valueOf(AccountType.POUPANCA.name()))
                .clientId(clientId)
                .build();

        Assertions.assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> service.create(accountRequestDto))
                .withMessage("Cliente não foi encontrado");

        verify(clientService).existsById(clientId);
    }

    @Test
    public void shouldMakeADeposit(){
        String numberAccount = "123";
        Account account = new Account();
        account.setBalance(new BigDecimal("10.00"));

        when(repository.findByAccountNumber(Mockito.any())).thenReturn(Optional.of(account));
        service.deposit(numberAccount, new BigDecimal("10.00"));
        BigDecimal accountBalance = service.getAccountBalance(numberAccount);

        Assertions.assertThat(accountBalance).isEqualTo(new BigDecimal("20.00"));
        verify(repository).save(any(Account.class));
    }

    @Test
    public void givenAnAccountWhenMakeADepositShouldBusinessException(){

        String numberAccount = "123";

        Assertions.assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> service.deposit(numberAccount, BigDecimal.ZERO))
                .withMessage("O valor do depósito deve ser positivo");
    }

    @Test
    public void shouldMakeAWithdraw(){
        String numberAccount = "123";
        Account account = new Account();
        account.setBalance(new BigDecimal("10.00"));

        when(repository.findByAccountNumber(Mockito.any())).thenReturn(Optional.of(account));
        service.withdraw(numberAccount, new BigDecimal("10.00"));
        BigDecimal accountBalance = service.getAccountBalance(numberAccount);

        Assertions.assertThat(accountBalance).isEqualTo(new BigDecimal("0.00"));
        verify(repository).save(any(Account.class));
    }

    @Test
    public void givenAnAccountWhenMakeAWithdrawShouldBusinessException(){

        String numberAccount = "123";

        Assertions.assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> service.withdraw(numberAccount, BigDecimal.ZERO))
                .withMessage("O valor de retirada deve ser positivo");
    }

}