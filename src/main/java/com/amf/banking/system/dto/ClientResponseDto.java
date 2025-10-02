package com.amf.banking.system.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class ClientResponseDto {

    private String id;

    private String fullName;

    private String cpf;

    private LocalDate birthDate;
}
