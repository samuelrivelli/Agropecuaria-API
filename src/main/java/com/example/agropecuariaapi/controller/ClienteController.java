package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.dto.ClienteDTO;
import com.example.agropecuariaapi.model.entity.Cliente;
import com.example.agropecuariaapi.model.entity.Endereco;
import com.example.agropecuariaapi.service.ClienteService;
import com.example.agropecuariaapi.service.EnderecoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping
    public ResponseEntity findAll() {
        List<Cliente> list = service.findAll();
        return ResponseEntity.ok(list.stream().map(ClienteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id){
        Optional<Cliente> cliente = service.findById(id);

        if(!cliente.isPresent()){
            return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(cliente.map(ClienteDTO::create));

    }

    @PostMapping
    public ResponseEntity post(@RequestBody ClienteDTO dto){
        try {
            Cliente Cliente = converter(dto);
            Endereco endereco = enderecoService.save(Cliente.getEndereco());
            Cliente.setEndereco(endereco);
            Cliente = service.save(Cliente);
            return new ResponseEntity(Cliente, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ClienteDTO dto) {
        try {
            Optional<Cliente> optionalCliente = service.findById(id);
            if (!optionalCliente.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente not found");
            }
            Cliente clienteExistente = optionalCliente.get();

            Cliente clienteAtualizado = converter(dto);

            Endereco enderecoAtualizado = enderecoService.save(clienteAtualizado.getEndereco());
            clienteAtualizado.setEndereco(enderecoAtualizado);

            clienteAtualizado.setId(id);

            clienteAtualizado = service.save(clienteAtualizado);

            return ResponseEntity.ok(clienteAtualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public Cliente converter(ClienteDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Cliente cliente = modelMapper.map(dto, Cliente.class);
        Endereco endereco = modelMapper.map(dto,Endereco.class);
        cliente.setEndereco(endereco);

        return cliente;
    }

}
