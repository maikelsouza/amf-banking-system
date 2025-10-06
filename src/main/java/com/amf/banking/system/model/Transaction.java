package com.amf.banking.system.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "transactions")
public class Transaction {

    @Id
    private String id;

    private String originAccountId;

    private String destinationAccountId;

    private BigDecimal amount;

    private String type;

    private LocalDateTime timestamp;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdDate;
}
