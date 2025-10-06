package com.amf.banking.system.controller;

import com.amf.banking.system.dto.ClientRequestDto;
import com.amf.banking.system.dto.ClientResponseDto;
import com.amf.banking.system.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/clients")
@Tag(name = "Clientes", description = "Operações com clientes")
public class ClientController {

    private final ClientService service;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar cliente", description = "Cria um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponseDto.class))
            })
    })
    public ClientResponseDto create(@RequestBody @Valid ClientRequestDto clientRequestDto) {
        return service.create(clientRequestDto);
    }
}
