package com.amf.banking.system.service;

import com.amf.banking.system.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ClientService {

    private final ClientRepository  repository;

    private final ModelMapper modelMapper;



}
