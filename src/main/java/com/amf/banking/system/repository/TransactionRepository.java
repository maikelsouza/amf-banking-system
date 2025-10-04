package com.amf.banking.system.repository;

import com.amf.banking.system.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

    List<Transaction> findByOriginAccountIdOrDestinationAccountIdAndTimestampBetween(
            String originAccountId,
            String destinationAccountId,
            LocalDateTime startDate,
            LocalDateTime endDate);
}
