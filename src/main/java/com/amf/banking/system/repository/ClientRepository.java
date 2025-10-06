package com.amf.banking.system.repository;

import com.amf.banking.system.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientRepository extends MongoRepository<Client, String> {

    boolean existsById(String id);

    boolean existsByCpf(String cpf);
}
