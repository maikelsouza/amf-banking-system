package com.amf.banking.system.controller;

import com.amf.banking.system.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService service;
}
