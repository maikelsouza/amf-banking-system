package com.amf.banking.system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Setter
@Getter
public class ClientRequestDto {

    @NotBlank(message = "O Nome é obrigatório")
    private String fullName;

    @NotBlank(message = "O CPF é obrigatório")
    @CPF(message = "CPF inválido")
    private String cpf;

    @NotNull(message = "A data de nascimento é obrigatória")
    private LocalDate birthDate;
}
