package com.amf.banking.system.dto;

import com.amf.banking.system.enums.AccountType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class AccountRequestDto {

    private String clientId;

    @NotNull(message = "O tipo da conta é obrigatório")
    private AccountType type;

}
