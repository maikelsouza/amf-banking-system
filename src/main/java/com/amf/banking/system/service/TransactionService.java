package com.amf.banking.system.service;

import com.amf.banking.system.dto.TransactionRequestDto;
import com.amf.banking.system.dto.TransactionResponseDto;
import com.amf.banking.system.model.Transaction;
import com.amf.banking.system.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class TransactionService {

    private final TransactionRepository repository;


    public void saveTransaction(Transaction transaction) {
        repository.save(transaction);
    }

    public List<TransactionResponseDto> findByAccountAndTimestampBetween(String number, LocalDateTime startDate, LocalDateTime endDate){
        List<Transaction> transactions = repository.findByOriginAccountIdOrDestinationAccountIdAndTimestampBetween(number, number, startDate, endDate);
        return TransactionRequestDto.convertToResponseDto(transactions);
    }
}
