package com.amf.banking.system.dto;

import com.amf.banking.system.model.Transaction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Setter
@Getter
@Builder
public class TransactionRequestDto {


    @NotBlank(message = "A conta origem é obrigatória")
    private String originAccountId;

    @NotBlank(message = "A conta destino é obrigatória")
    private String destinationAccountId;

    @NotNull(message = "A quantia é obrigatória")
    private BigDecimal amount;


    public static Transaction convertToDocument(TransactionRequestDto transactionRequestDto) {
        return Transaction.builder()
                .originAccountId(transactionRequestDto.getOriginAccountId())
                .destinationAccountId(transactionRequestDto.getDestinationAccountId())
                .amount(transactionRequestDto.getAmount())
                .type("TRANSFERÊNCIA")
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static List<TransactionResponseDto> convertToResponseDto(List<Transaction> transactions) {
        return transactions.stream().map(TransactionResponseDto::convertToDto).toList();
    }


}
