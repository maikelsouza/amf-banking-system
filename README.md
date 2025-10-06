# API - AMF PROMOTORA - SISTEMA BANCÁRIO 

## Objetivo
API criada para atender o desafio técnico para a vaga de engenheiro de software java sênior da empresa AMF Promotora.

## Descrição do Projeto
Desenvolvimento de um sistema bancário interno voltado para gestão de contas e movimentações financeiras de clientes.
A ideia é criar uma API REST para o back-office e, opcionalmente, uma interface com Vaadin para operadores internos.

## Tecnologias Usadas

<ul>
  <li>Java - 21</li>
  <li>Spring boot - 3.5.6</li>
  <li>Spring Boot Starter Web - 3.5.6</li>
  <li>Spring Boot Starter Data Mongodb - 3.5.6</li>
  <li>Spring Boot Starter Validation - 3.5.6</li>
  <li>Junit Jupiter 5.17.0</li>
  <li>Mockito - 5.17.0</li>  
  <li>Lombok - 1.18.40</li>
  <li>ModelMapper - 2.2.5</li>
  <li>Stella Core - 2.2.2</li>
  <li>Test Container - 1.19.0</li>
  <li>Openapi - 2.8.13</li>  
</ul>

## Passo a passo para executar a API

1. Inicialize o container docker via comando: `docker-compose up -d`  
2. [Executar a classe AmfBankingSystemApplication](src/main/java/com/amf/banking/system/AmfBankingSystemApplication.java)

## Testes

Para executar todos os testes, você deve executar o comando maven `mvn test` 

1. [Arquivos de teste de integração](src/test/java/com/amf/banking/system/integration)
2. [Arquivos de testes unitários](src/test/java/com/amf/banking/system/service)

## Documentação da API

### Swagger (necessário inicializar a aplicação)

[Documentação Swagger](http://localhost:8080/swagger-ui/index.html)

### Download do Arquivo Json (Postman)

[📥 Baixar Postman](src/main/resources/postman/Biblioteca-rest.postman_collection.json)


