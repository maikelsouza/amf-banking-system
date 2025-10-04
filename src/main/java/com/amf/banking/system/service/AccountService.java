package com.amf.banking.system.service;

import com.amf.banking.system.dto.AccountRequestDto;
import com.amf.banking.system.dto.AccountResponseDto;
import com.amf.banking.system.dto.TransactionResponseDto;
import com.amf.banking.system.model.Account;
import com.amf.banking.system.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
@Service
public class AccountService {

    private final AccountRepository repository;

    private final ModelMapper modelMapper;

    private final ClientService clientService;

    private final TransactionService transactionService;

    public AccountResponseDto create(AccountRequestDto accountRequestDto) {
        if (!clientService.existsById(accountRequestDto.getClientId())) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }
        Account account = modelMapper.map(accountRequestDto, Account.class);
        account.setAccountNumber(generateAccountNumber());
        Account accountBd = repository.save(account);
        return  modelMapper.map(accountBd, AccountResponseDto.class);
    }

    public BigDecimal getAccountBalance(String number){
        Account account = getAccount(number);
        return account.getBalance();
    }

    public void deposit(String number, BigDecimal amount){
        if (amount.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("O valor do depósito deve ser positivo");
        }
        Account account = getAccount(number);
        account.setBalance(account.getBalance().add(amount));
        repository.save(account);
    }

    public void withdraw(String number, BigDecimal amount){
        if (amount.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("O valor de retirada deve ser positivo");
        }
        Account account = getAccount(number);
        account.setBalance(account.getBalance().subtract(amount));
        repository.save(account);
    }

    public List<TransactionResponseDto> getTransactions(String number, LocalDateTime startDate, LocalDateTime endDate){
        return transactionService.findByAccountAndTimestampBetween(number, startDate, endDate);
    }

    private Account getAccount(String number) {
        return repository.findByAccountNumber(number).
                orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
    }

    private String generateAccountNumber() {
        String number;
        do {
            number = String.format("%08d", new Random().nextInt(100_000_000));
        } while (repository.existsByAccountNumber(number));
        return number;
    }

}
