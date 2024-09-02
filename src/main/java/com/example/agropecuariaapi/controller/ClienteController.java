package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.dto.ClienteDTO;
import com.example.agropecuariaapi.model.entity.Cliente;
import com.example.agropecuariaapi.model.entity.Endereco;
import com.example.agropecuariaapi.service.ClienteService;
import com.example.agropecuariaapi.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    @Operation(
            summary = "Mostra todos os clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes encontrados"),
            @ApiResponse(responseCode = "404", description = "Clientes não encontrados")
    })
    public ResponseEntity findAll() {
        List<Cliente> list = service.findAll();
        return ResponseEntity.ok(list.stream().map(ClienteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Mostra um único cliente baseado no seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrados"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity findById(@PathVariable("id") Long id){
        Optional<Cliente> cliente = service.findById(id);

        if(!cliente.isPresent()){
            return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(cliente.map(ClienteDTO::create));

    }

    @PostMapping
    @Operation(
            summary = "Salva um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente salvo"),
            @ApiResponse(responseCode = "400", description = "Erro ao salvar cliente")
    })
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
    @Operation(
            summary = "Exclui um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id){
        Optional<Cliente> cliente = service.findById(id);

        if(!cliente.isPresent()){
            return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }

        try {
            service.excluir(cliente.get());
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("O cliente nao pode ser excluido pois possui vendas associadas a ele");
        }
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualiza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
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
