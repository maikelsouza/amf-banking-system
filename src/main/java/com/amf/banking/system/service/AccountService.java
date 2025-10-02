package com.amf.banking.system.service;

import com.amf.banking.system.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AccountService {

    private final AccountRepository repository;



}
