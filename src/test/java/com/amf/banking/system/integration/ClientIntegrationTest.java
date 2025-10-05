package com.amf.banking.system.integration;

import com.amf.banking.system.model.Client;
import com.amf.banking.system.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
public class ClientIntegrationTest {

    @Autowired
    private ClientRepository repository;

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:8.0");

    @DynamicPropertySource
    static void setMongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }


    @Test
    void shouldSaveAndFindAClient() {
        Client client = new Client();
        client.setCpf("003.426.839-18");
        client.setBirthDate(LocalDate.of(1980,10, 3));
        client.setFullName("Maikel Joel de Souza");

        client = repository.save(client);

        assertThat(client.getId()).isNotNull();
        Client clientDb = repository.findById(client.getId()).orElse(null);
        assertThat(clientDb).isNotNull();
        assertThat(clientDb.getCpf()).isEqualTo("003.426.839-18");
        assertThat(clientDb.getFullName()).isEqualTo("Maikel Joel de Souza");
        assertThat(clientDb.getBirthDate()).isEqualTo(LocalDate.of(1980,10, 3));
    }

    @Test
    void shouldSaveAndVerifyExistAClient() {
        Client client = new Client();
        client.setCpf("114.418.810-50");
        client.setBirthDate(LocalDate.of(1980,10, 3));
        client.setFullName("Maikel Joel de Souza");

        client = repository.save(client);
        boolean exists = repository.existsById(client.getId());
        assertThat(exists).isTrue();
    }

}

