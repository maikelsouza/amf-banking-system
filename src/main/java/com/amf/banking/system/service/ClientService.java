package com.amf.banking.system.service;

import com.amf.banking.system.dto.ClientRequestDto;
import com.amf.banking.system.dto.ClientResponseDto;
import com.amf.banking.system.model.Client;
import com.amf.banking.system.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ClientService {

    private final ClientRepository  repository;

    private final ModelMapper modelMapper;

    public ClientResponseDto create(ClientRequestDto clientRequestDto) {
        Client client = modelMapper.map(clientRequestDto, Client.class);
        Client clientBd = repository.save(client);
        return  modelMapper.map(clientBd, ClientResponseDto.class);
    }

    public boolean existsById(String id){
        return repository.existsById(id);
    }



}
