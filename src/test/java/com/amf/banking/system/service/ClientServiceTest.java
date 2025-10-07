package com.amf.banking.system.service;

import com.amf.banking.system.dto.ClientRequestDto;
import com.amf.banking.system.dto.ClientResponseDto;
import com.amf.banking.system.exception.BusinessException;
import com.amf.banking.system.model.Client;
import com.amf.banking.system.repository.ClientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @InjectMocks
    private ClientService service;

    @Mock
    private ClientRepository repository;

    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        service = new ClientService(repository, modelMapper);
    }


    @Test
    public void shouldCreatedAClient(){
        String cpf = "003.426.839-18";
        String fullName = "Maikel Joel de Souza";
        LocalDate birthDate = LocalDate.of(1980, 10, 3);

        Client client = new Client();
        client.setId("123");
        client.setFullName(fullName);
        client.setCpf(cpf);
        client.setBirthDate(birthDate);

        when(repository.existsByCpf(Mockito.any())).thenReturn(false);
        when(repository.save(Mockito.any())).thenReturn(client);

        ClientRequestDto clientRequestDto = ClientRequestDto.builder()
                .cpf(cpf)
                .birthDate(birthDate)
                .fullName(fullName)
                .build();

        ClientResponseDto clientResponseDto = service.create(clientRequestDto);

        assertNotNull(clientResponseDto);
        Assertions.assertThat(clientResponseDto.getId()).isNotNull();
        Assertions.assertThat(clientResponseDto.getCpf()).isEqualTo(cpf);
        Assertions.assertThat(clientResponseDto.getFullName()).isEqualTo(fullName);
        Assertions.assertThat(clientResponseDto.getBirthDate()).isEqualTo(birthDate);

        verify(repository).save(any(Client.class));
        verify(repository).existsByCpf(cpf);
    }

    @Test
    public void givenAClientWhenAlreadyHasRegisteredClientShouldBusinessException(){

        when(repository.existsByCpf(Mockito.any())).thenReturn(true);

        String cpf = "003.426.839-18";
        String fullName = "Maikel Joel de Souza";
        LocalDate birthDate = LocalDate.of(1980, 10, 3);

        ClientRequestDto clientRequestDto = ClientRequestDto.builder()
                .cpf(cpf)
                .birthDate(birthDate)
                .fullName(fullName)
                .build();

        Assertions.assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> service.create(clientRequestDto))
                .withMessage("Já existe um cliente cadastrado com esse cpf");
    }

    @Test
    public void givenAClientWhenDateOfBirthIsGreaterThanCurrentDayShouldBusinessException(){

        when(repository.existsByCpf(Mockito.any())).thenReturn(false);

        String cpf = "003.426.839-18";
        String fullName = "Maikel Joel de Souza";
        LocalDate birthDate = LocalDate.now().plusDays(1);

        ClientRequestDto clientRequestDto = ClientRequestDto.builder()
                .cpf(cpf)
                .birthDate(birthDate)
                .fullName(fullName)
                .build();

        Assertions.assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> service.create(clientRequestDto))
                .withMessage("A data de nascimento deve ser inferior ao dia atual");

        verify(repository).existsByCpf(cpf);
    }



}