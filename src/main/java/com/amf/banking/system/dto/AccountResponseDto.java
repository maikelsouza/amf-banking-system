package com.amf.banking.system.dto;

import com.amf.banking.system.enums.AccountType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class AccountResponseDto {


    private String id;

    private String clientId;

    private String accountNumber;

    private AccountType type;

    private BigDecimal balance;

}
