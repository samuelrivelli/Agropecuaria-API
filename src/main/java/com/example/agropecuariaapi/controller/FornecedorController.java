package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.dto.FornecedorDTO;
import com.example.agropecuariaapi.model.entity.Fornecedor;
import com.example.agropecuariaapi.model.entity.Endereco;
import com.example.agropecuariaapi.service.EnderecoService;
import com.example.agropecuariaapi.service.FornecedorService;
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

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorService service;

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping
    @Operation(
            summary = "Mostra todos os fornecedores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fornecedores encontrados"),
            @ApiResponse(responseCode = "404", description = "Fornecedores não encontrados")
    })
    public ResponseEntity findAll() {
        List<Fornecedor> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Mostra um único fornecedor baseado no ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fornecedor encontrado"),
            @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado")
    })
    public ResponseEntity findById(@PathVariable("id") Long id){
        Optional Fornecedor = service.findById(id);

        if(!Fornecedor.isPresent()){
            return new ResponseEntity<>("Fornecedor não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(Fornecedor);

    }

    @PostMapping
    @Operation(
            summary = "Salva um fornecedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Fornecedor salvo"),
            @ApiResponse(responseCode = "400", description = "Erro ao salvar fornecedor")
    })
    public ResponseEntity post(@RequestBody FornecedorDTO dto){
        try {
            Fornecedor Fornecedor = converter(dto);
            Endereco endereco = enderecoService.save(Fornecedor.getEndereco());
            Fornecedor.setEndereco(endereco);
            Fornecedor = service.save(Fornecedor);
            return new ResponseEntity(Fornecedor, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("{id}")
    @Operation(
            summary = "Exclui um fornecedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Fornecedor excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id){
        Optional<Fornecedor> Fornecedor = service.findById(id);

        if(!Fornecedor.isPresent()){
            return new ResponseEntity<>("Fornecedor não encontrado", HttpStatus.NOT_FOUND);
        }

        try {
            service.excluir(Fornecedor.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (DataIntegrityViolationException e){
            return ResponseEntity.badRequest().body("O fornecedor não pode ser excluído pois possui compras atreladas a ele");
        }

    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualiza um fornecedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fornecedor atualizado"),
            @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado")
    })
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody FornecedorDTO dto) {
        try {
            Fornecedor fornecedorAtualizado = converter(dto);

            fornecedorAtualizado.setId(id);

            fornecedorAtualizado = service.save(fornecedorAtualizado);

            return ResponseEntity.ok(fornecedorAtualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Fornecedor converter(FornecedorDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Fornecedor fornecedor = modelMapper.map(dto, Fornecedor.class);
        Endereco endereco = modelMapper.map(dto,Endereco.class);
        fornecedor.setEndereco(endereco);

        return fornecedor;
    }



}
