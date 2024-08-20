package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.dto.ProdutoDTO;
import com.example.agropecuariaapi.model.entity.Produto;
import com.example.agropecuariaapi.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @GetMapping
    @Operation(
            summary = "Mostra todos os produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos encontrados"),
            @ApiResponse(responseCode = "404", description = "Produtos não encontrados")
    })
    public ResponseEntity findAll() {
        List<Produto> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Mostra um único produto baseado no ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity findById(@PathVariable("id") Long id){
        Optional Produto = service.findById(id);

        if(!Produto.isPresent()){
            return new ResponseEntity<>("Produto não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(Produto);

    }

    @PostMapping
    @Operation(
            summary = "Salva um produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto salvo"),
            @ApiResponse(responseCode = "400", description = "Erro ao salvar produto")
    })
    public ResponseEntity post(@RequestBody ProdutoDTO dto){
        try{
            Produto produto = converter(dto);
            produto = service.save(produto);

            return new ResponseEntity(produto, HttpStatus.CREATED);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "Exclui um produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id){
        Optional<Produto> Produto = service.findById(id);

        if(!Produto.isPresent()){
            return new ResponseEntity<>("Produto não encontrado", HttpStatus.NOT_FOUND);
        }

        service.excluir(Produto.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    @Operation(
            summary = "Atualiza um produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody ProdutoDTO dto) {
        if (!service.findById(id).isPresent()) {
            return new ResponseEntity("Produto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Produto Produto = converter(dto);
            Produto.setId(id);
            service.save(Produto);
            return ResponseEntity.ok(Produto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Produto converter(ProdutoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Produto.class);
    }

}
