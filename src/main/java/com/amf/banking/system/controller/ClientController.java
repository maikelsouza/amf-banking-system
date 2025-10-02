package com.amf.banking.system.controller;

import com.amf.banking.system.dto.ClientRequestDto;
import com.amf.banking.system.dto.ClientResponseDto;
import com.amf.banking.system.service.ClientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final ClientService service;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponseDto create(@RequestBody @Valid ClientRequestDto clientRequestDto) {
        return service.create(clientRequestDto);
    }
}
