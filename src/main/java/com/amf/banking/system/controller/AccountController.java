package com.amf.banking.system.controller;

import com.amf.banking.system.dto.AccountRequestDto;
import com.amf.banking.system.dto.AccountResponseDto;
import com.amf.banking.system.dto.DepositAmountRequestDto;
import com.amf.banking.system.dto.TransactionResponseDto;
import com.amf.banking.system.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/accounts")
@Tag(name = "Contas Bancárias", description = "Operações com contas bancárias")
public class AccountController {

    private final AccountService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar conta bancária", description = "Cria uma conta bancária")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Conta criada", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AccountResponseDto.class))
            }),
            @ApiResponse(responseCode = "204", description = "Cliente não encontrado", content = {
                    @Content(mediaType ="application/json",  schema = @Schema(implementation = ProblemDetail.class))
            })
    })
    public AccountResponseDto create(@RequestBody @Valid AccountRequestDto accountRequestDto) {
        return service.create(accountRequestDto);
    }

    @GetMapping("/{id}/balance")
    @Operation(summary = "Recuperar Saldo", description = "Recupera o saldo de uma conta bancária")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saldo Recuperado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BigDecimal.class))
            }),
            @ApiResponse(responseCode = "204", description = "Cliente não encontrado", content = {
                    @Content(mediaType ="application/json",  schema = @Schema(implementation = ProblemDetail.class))
            })
    })
    public BigDecimal getAccountBalance(@PathVariable String id) {
        return service.getAccountBalance(id);
    }


    @PatchMapping("/{id}/deposit")
    @Operation(summary = "Realizar Depósito", description = "Realiza um depósito em uma conta bancária")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Depósito efetuado"),
            @ApiResponse(responseCode = "204", description = "Cliente não encontrado", content = {
                    @Content(mediaType ="application/json",  schema = @Schema(implementation = ProblemDetail.class))
            }),
            @ApiResponse(responseCode = "400", description = "O valor do depósito deve ser positivo", content = {
                    @Content(mediaType ="application/json",  schema = @Schema(implementation = ProblemDetail.class))
            })
    })
    public void deposit(@PathVariable String id,
                        @RequestBody DepositAmountRequestDto depositAmountRequestDto) {
        service.deposit(id, depositAmountRequestDto.getAmount());
    }

    @Operation(summary = "Recuperar Transações", description = "Recupera as transações uma conta bancária")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transações recuperadas", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BigDecimal.class))
            }),
            @ApiResponse(responseCode = "204", description = "Cliente não encontrado", content = {
                    @Content(mediaType ="application/json",  schema = @Schema(implementation = ProblemDetail.class))
            })
    })
    @GetMapping("/{id}/transactions")
    public List<TransactionResponseDto> getTransactions(
            @PathVariable String id, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDateTime endDate) {

        return service.getTransactions(id, startDate, endDate);
    }

}
