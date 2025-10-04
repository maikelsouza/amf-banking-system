package com.amf.banking.system.repository;

import com.amf.banking.system.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account, String> {

    boolean existsByAccountNumber(String number);

    Optional<Account> findByAccountNumber(String number);
}
