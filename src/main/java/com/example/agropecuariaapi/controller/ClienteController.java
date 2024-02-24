package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.model.entity.Cliente;
import com.example.agropecuariaapi.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public ResponseEntity findAll() {
        List<Cliente> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

}
