package com.amf.banking.system.service;

import com.amf.banking.system.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TransactionService {

    private final TransactionRepository repository;
}
