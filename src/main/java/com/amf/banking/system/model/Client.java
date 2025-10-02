package com.amf.banking.system.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "clients")
public class Client {

    @Id
    private String id;

    private String fullName;

    @Indexed(unique = true)
    private String cpf;

    private LocalDate birthDate;
}
