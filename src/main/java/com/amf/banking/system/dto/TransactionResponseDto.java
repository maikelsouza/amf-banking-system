package com.amf.banking.system.dto;

import com.amf.banking.system.model.Transaction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Setter
@Getter
@Builder
public class TransactionResponseDto {


    @NotBlank(message = "A conta origem é obrigatória")
    private String originAccountId;

    @NotBlank(message = "A conta destino é obrigatória")
    private String destinationAccountId;

    @NotNull(message = "A quantia é obrigatória")
    private BigDecimal amount;

    private LocalDateTime timestamp;


    public static TransactionResponseDto convertToDto(Transaction transaction) {
        return TransactionResponseDto.builder()
                .amount(transaction.getAmount())
                .originAccountId(transaction.getOriginAccountId())
                .destinationAccountId(transaction.getDestinationAccountId())
                .timestamp(transaction.getTimestamp())
                .build();
    }

}
