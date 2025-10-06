package com.amf.banking.system.integration;

import com.amf.banking.system.model.Transaction;
import com.amf.banking.system.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
public class TransactionIntegrationTest {

    @Autowired
    private TransactionRepository repository;

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:8.0");

    @DynamicPropertySource
    static void setMongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }


    @Test
    void shouldReturnTransactionsAClientForATimePeriod() {

        String accountClient1 = "client1";
        String accountClient2 = "client2";
        String accountClient3 = "client3";
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        Transaction transaction1 = Transaction.builder()
                .originAccountId(accountClient1)
                .destinationAccountId(accountClient2)
                .amount(new BigDecimal("10.00"))
                .type("TRANSFERÊNCIA")
                .timestamp(today)
                .build();

        Transaction transaction2 = Transaction.builder()
                .originAccountId(accountClient1)
                .destinationAccountId(accountClient3)
                .amount(new BigDecimal("20.00"))
                .type("TRANSFERÊNCIA")
                .timestamp(tomorrow)
                .build();

        Transaction transaction3 = Transaction.builder()
                .originAccountId(accountClient2)
                .destinationAccountId(accountClient1)
                .amount(new BigDecimal("20.00"))
                .type("TRANSFERÊNCIA")
                .timestamp(tomorrow)
                .build();

        Transaction transaction4 = Transaction.builder()
                .originAccountId(accountClient2)
                .destinationAccountId(accountClient3)
                .amount(new BigDecimal("20.00"))
                .type("TRANSFERÊNCIA")
                .timestamp(tomorrow)
                .build();

        repository.save(transaction1);
        repository.save(transaction2);
        repository.save(transaction3);
        repository.save(transaction4);

        List<Transaction> transactions = repository.findByOriginAccountIdOrDestinationAccountIdAndTimestampBetween(accountClient1, accountClient1, today, LocalDateTime.now().plusDays(1));

        assertThat(transactions.size()).isEqualTo(3);
        assertThat(transactions.stream()
                .anyMatch(tx -> tx.getOriginAccountId().equals(accountClient1)
                        || tx.getDestinationAccountId().equals(accountClient1)))
                .isTrue();
        assertThat(transactions.stream()
                .anyMatch(tx -> tx.getTimestamp().getDayOfYear() == today.getDayOfYear()
                        || tx.getTimestamp().getDayOfYear() == tomorrow.getDayOfYear()))
                .isTrue();
    }

}

