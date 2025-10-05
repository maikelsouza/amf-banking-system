package com.amf.banking.system.integration;

import com.amf.banking.system.enums.AccountType;
import com.amf.banking.system.model.Account;
import com.amf.banking.system.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
public class AccountIntegrationTest {

    @Autowired
    private AccountRepository repository;

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:8.0");

    @DynamicPropertySource
    static void setMongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }


    @Test
    void shouldSaveAndFindAnAccount() {
        Account account = new Account();
        account.setClientId("client123");
        account.setAccountNumber("ACC-001");
        account.setBalance(new BigDecimal("100.00"));
        account.setType(AccountType.SAVINGS);

        account = repository.save(account);

        assertThat(account.getId()).isNotNull();

        Account accountDb = repository.findByAccountNumber("ACC-001").orElse(null);
        assertThat(accountDb).isNotNull();
        assertThat(accountDb.getClientId()).isEqualTo("client123");
        assertThat(accountDb.getBalance()).isEqualByComparingTo("100.00");
    }

}
