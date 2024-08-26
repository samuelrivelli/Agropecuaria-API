package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.dto.CarrinhoDTO;
import com.example.agropecuariaapi.dto.ClienteDTO;
import com.example.agropecuariaapi.model.entity.Carrinho;
import com.example.agropecuariaapi.service.CarrinhoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carrinhos")
public class CarrinhoController {

    @Autowired
    private CarrinhoService service;

    @GetMapping
    @Operation(
            summary = "Mostra todos os carrinhos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrinhos encontrados"),
            @ApiResponse(responseCode = "404", description = "Carrinhos não encontrados")
    })
    public ResponseEntity findAll() {
        List<Carrinho> list = service.findAll();
        return ResponseEntity.ok(list.stream().map(CarrinhoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Mostra um único carrinho baseado no seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrinho encontrado"),
            @ApiResponse(responseCode = "404", description = "Carrinho não encontrado")
    })
    public ResponseEntity findById(@PathVariable("id") Long id){
        Optional<Carrinho> cliente = service.findById(id);

        if(!cliente.isPresent()){
            return new ResponseEntity<>("Carrinho não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(cliente.map(CarrinhoDTO::create));

    }

    @PostMapping
    @Operation(
            summary = "Salva um carrinho")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Carrinho salvo"),
            @ApiResponse(responseCode = "400", description = "Erro ao salvar carrinho")
    })
    public ResponseEntity createCarrinho(Carrinho carrinho){
        service.save(carrinho);
        return new ResponseEntity(carrinho, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Exclui um carrinho")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Carrinho excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Carrinho não encontrado")
    })
    public ResponseEntity delete(@PathVariable("id") Long id){
        Optional<Carrinho> carrinho = service.findById(id);
        if(!carrinho.isPresent()){
            return new ResponseEntity<>("Carrinho não encontrado", HttpStatus.NOT_FOUND);
        }

        service.excluir(carrinho.get());

        return ResponseEntity.noContent().build();

    }

    @PostMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Carrinho carrinho){
        Optional<Carrinho> c = service.findById(id);

        if(!c.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carrinho not found");
        }
        service.save(carrinho);
        return ResponseEntity.ok(carrinho);
    }




}
