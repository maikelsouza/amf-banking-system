package com.amf.banking.system.service;

import com.amf.banking.system.dto.TransactionRequestDto;
import com.amf.banking.system.exception.BusinessException;
import com.amf.banking.system.model.Transaction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@AllArgsConstructor
@Service
public class TransferService {

    private final AccountService accountService;

    private final TransactionService transactionService;

    public void transfer(TransactionRequestDto transactionRequestDto){

        if (transactionRequestDto.getOriginAccountId().equalsIgnoreCase(transactionRequestDto.getDestinationAccountId())){
            throw new BusinessException("A conta de origem e destino devem ser distintas");
        }

        BigDecimal accountBalance = accountService.getAccountBalance(transactionRequestDto.getOriginAccountId());
        if (accountBalance.compareTo(transactionRequestDto.getAmount()) < 0){
            throw new BusinessException("A conta de origem não tem saldo");
        }

        accountService.withdraw(transactionRequestDto.getOriginAccountId(), transactionRequestDto.getAmount());
        accountService.deposit(transactionRequestDto.getDestinationAccountId(), transactionRequestDto.getAmount());

        Transaction transaction = TransactionRequestDto.convertToDocument(transactionRequestDto);

        transactionService.saveTransaction(transaction);
    }
}
