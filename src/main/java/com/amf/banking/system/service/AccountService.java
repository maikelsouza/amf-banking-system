package com.amf.banking.system.service;

import com.amf.banking.system.dto.AccountRequestDto;
import com.amf.banking.system.dto.AccountResponseDto;
import com.amf.banking.system.model.Account;
import com.amf.banking.system.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@AllArgsConstructor
@Service
public class AccountService {

    private final AccountRepository repository;

    private final ModelMapper modelMapper;

    private final ClientService clientService;

    public AccountResponseDto create(AccountRequestDto accountRequestDto) {
        if (!clientService.existsById(accountRequestDto.getClientId())) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }
        Account account = modelMapper.map(accountRequestDto, Account.class);
        account.setAccountNumber(generateAccountNumber());
        Account accountBd = repository.save(account);
        return  modelMapper.map(accountBd, AccountResponseDto.class);
    }

    public BigDecimal getAccountBalance(String id){
        Account account = getAccount(id);
        return account.getBalance();
    }

    public void deposit(String id, BigDecimal amount){
        if (amount.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("O valor do depósito deve ser positivo");
        }
        Account account = getAccount(id);
        account.setBalance(account.getBalance().add(amount));
        repository.save(account);
    }

    private Account getAccount(String id) {
        return repository.findById(id).
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
