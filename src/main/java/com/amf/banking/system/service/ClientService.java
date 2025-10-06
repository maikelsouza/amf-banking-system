package com.amf.banking.system.service;

import com.amf.banking.system.dto.ClientRequestDto;
import com.amf.banking.system.dto.ClientResponseDto;
import com.amf.banking.system.exception.BusinessException;
import com.amf.banking.system.model.Client;
import com.amf.banking.system.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@AllArgsConstructor
@Service
public class ClientService {

    private final ClientRepository  repository;

    private final ModelMapper modelMapper;

    public ClientResponseDto create(ClientRequestDto clientRequestDto) {
        if (repository.existsByCpf(clientRequestDto.getCpf())){
            throw new BusinessException("Já existe um cliente cadastrado com esse cpf");
        }

        if (clientRequestDto.getBirthDate().isAfter(LocalDate.now())){
            throw new BusinessException("A data de nascimento deve ser inferior ao dia atual");
        }

        Client client = modelMapper.map(clientRequestDto, Client.class);
        Client clientBd = repository.save(client);
        return  modelMapper.map(clientBd, ClientResponseDto.class);
    }

    public boolean existsById(String id){
        return repository.existsById(id);
    }



}
