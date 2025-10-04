package com.amf.banking.system.dto;

import com.amf.banking.system.model.Transaction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Setter
@Getter
public class TransactionRequestDto {


    @NotBlank(message = "A conta origem é obrigatória")
    private String originAccountId;

    @NotBlank(message = "A conta destino é obrigatória")
    private String destinationAccountId;

    @NotNull(message = "A quantia é obrigatória")
    private BigDecimal amount;


    public static Transaction mapToTransaction(TransactionRequestDto dto) {
        return Transaction.builder()
                .originAccountId(dto.getOriginAccountId())
                .destinationAccountId(dto.getDestinationAccountId())
                .amount(dto.getAmount())
                .build();
    }
}
