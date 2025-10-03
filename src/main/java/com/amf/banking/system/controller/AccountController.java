package com.amf.banking.system.controller;

import com.amf.banking.system.dto.AccountRequestDto;
import com.amf.banking.system.dto.AccountResponseDto;
import com.amf.banking.system.dto.DepositAmountRequestDto;
import com.amf.banking.system.service.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponseDto create(@RequestBody @Valid AccountRequestDto accountRequestDto) {
        return service.create(accountRequestDto);
    }

    @GetMapping("/{id}/balance")
    public BigDecimal getAccountBalance(@PathVariable String id) {
        return service.getAccountBalance(id);
    }

    @PatchMapping("/{id}/deposit")
    public void deposit(@PathVariable String id,
                              @RequestBody DepositAmountRequestDto depositAmountRequestDto) {
        service.deposit(id, depositAmountRequestDto.getAmount());
    }

}
