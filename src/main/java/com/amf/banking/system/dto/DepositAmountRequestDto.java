package com.amf.banking.system.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DepositAmountRequestDto {

    private BigDecimal amount;
}
