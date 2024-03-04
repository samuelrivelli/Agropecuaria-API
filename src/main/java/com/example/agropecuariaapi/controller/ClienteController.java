package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.model.entity.Cliente;
import com.example.agropecuariaapi.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id){
        Optional cliente = service.findById(id);

        if(!cliente.isPresent()){
            return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(cliente);

    }
    @PostMapping
    public ResponseEntity post(@RequestBody Cliente cliente){
        cliente = service.salvar(cliente);
        return ResponseEntity.ok().body(cliente);
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id){
        Optional<Cliente> cliente = service.findById(id);

        if(!cliente.isPresent()){
            return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }

        service.excluir(cliente.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody Cliente clienteAtualizado){

        Optional<Cliente> clienteExistente = service.findById(id);

        if(!service.findById(id).isPresent()){
            return new ResponseEntity<>("Cliente não encontrado",HttpStatus.NOT_FOUND);
        }

        Cliente cliente = clienteExistente.get();
        cliente.setNome(clienteAtualizado.getNome());
        cliente.setCpf(clienteAtualizado.getCpf());
        cliente.setEmail(clienteAtualizado.getEmail());
        cliente.setCpf(clienteAtualizado.getCpf());
        cliente.setEndereco(clienteAtualizado.getEndereco());

        service.salvar(cliente);
        return ResponseEntity.ok(cliente);
    }

}
