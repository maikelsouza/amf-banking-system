package com.amf.banking.system.controller;


import com.amf.banking.system.dto.TransactionRequestDto;
import com.amf.banking.system.service.TransferService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransferService service;


    @PostMapping
    public void transfer(@RequestBody @Valid TransactionRequestDto transactionRequestDto) {
         service.transfer(transactionRequestDto);
    }
}
