package com.amf.banking.system.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class ClientRequestDto {


    private String fullName;

    private String cpf;

    private LocalDate birthDate;
}
