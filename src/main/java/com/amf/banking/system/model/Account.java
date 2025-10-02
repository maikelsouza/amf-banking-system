package com.amf.banking.system.model;

import com.amf.banking.system.enums.AccountType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Document(collection = "accounts")
public class Account {

    @Id
    private String id;

    private String clientId;

    @Indexed(unique = true)
    private String accountNumber;

    private AccountType type;

    private BigDecimal balance = BigDecimal.ZERO;

    private LocalDateTime createdAt = LocalDateTime.now();
}
