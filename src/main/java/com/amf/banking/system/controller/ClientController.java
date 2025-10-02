package com.amf.banking.system.controller;

import com.amf.banking.system.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final ClientService service;
}
