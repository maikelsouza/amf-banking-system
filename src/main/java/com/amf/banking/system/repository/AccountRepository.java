package com.amf.banking.system.repository;

import com.amf.banking.system.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {

    boolean existsByAccountNumber(String number);
}
