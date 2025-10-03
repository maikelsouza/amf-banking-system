package com.amf.banking.system.config;

import com.amf.banking.system.dto.AccountRequestDto;
import com.amf.banking.system.model.Account;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.typeMap(AccountRequestDto.class, Account.class)
                .addMappings(m -> m.skip(Account::setId));
        return mapper;
    }
}
