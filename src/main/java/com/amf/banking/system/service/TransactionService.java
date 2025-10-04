package com.amf.banking.system.service;

import com.amf.banking.system.dto.TransactionRequestDto;
import com.amf.banking.system.model.Transaction;
import com.amf.banking.system.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@AllArgsConstructor
@Service
public class TransactionService {

    private final TransactionRepository repository;

    private final AccountService accountService;


    public void transfer(TransactionRequestDto transactionRequestDto){

        if (transactionRequestDto.getOriginAccountId().equalsIgnoreCase(transactionRequestDto.getDestinationAccountId())){
            throw new IllegalArgumentException("A conta de origem e destino devem ser distintas");
        }

        BigDecimal accountBalance = accountService.getAccountBalance(transactionRequestDto.getOriginAccountId());
        if (accountBalance.compareTo(transactionRequestDto.getAmount()) <= 0){
            throw new IllegalArgumentException("A conta de origem não tem saldo");
        }

        accountService.deposit(transactionRequestDto.getDestinationAccountId(), transactionRequestDto.getAmount());
        accountService.withdraw(transactionRequestDto.getOriginAccountId(), transactionRequestDto.getAmount());

        Transaction transaction = TransactionRequestDto.mapToTransaction(transactionRequestDto);

        repository.save(transaction);

    }
}
