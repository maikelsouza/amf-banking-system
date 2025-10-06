package com.amf.banking.system.controller;


import com.amf.banking.system.dto.TransactionRequestDto;
import com.amf.banking.system.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/transactions")
@Tag(name = "Transações", description = "Operações referente as transações bancárias")
public class TransactionController {

    private final TransferService service;


    @PostMapping
    @Operation(summary = "Realizar transferência bancária", description = "Faz a transferência bancária de uma conta para outra")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transferência realizada criado"),
            @ApiResponse(responseCode = "400", description = "A conta de origem e destino devem ser distintas / A conta de origem não tem saldo", content = {
                    @Content(mediaType ="application/json",  schema = @Schema(implementation = ProblemDetail.class))
            })
    })
    public void transfer(@RequestBody @Valid TransactionRequestDto transactionRequestDto) {
         service.transfer(transactionRequestDto);
    }
}
