package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.dto.FornecedorDTO;
import com.example.agropecuariaapi.model.entity.Endereco;
import com.example.agropecuariaapi.model.entity.Fornecedor;
import com.example.agropecuariaapi.service.FornecedorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public ResponseEntity findAll() {
        List<Fornecedor> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id){
        Optional Fornecedor = service.findById(id);

        if(!Fornecedor.isPresent()){
            return new ResponseEntity<>("Fornecedor não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(Fornecedor);

    }

    @PostMapping
    public ResponseEntity post(@RequestBody Fornecedor Fornecedor){
        Fornecedor = service.save(Fornecedor);
        return ResponseEntity.ok().body(Fornecedor);
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id){
        Optional<Fornecedor> Fornecedor = service.findById(id);

        if(!Fornecedor.isPresent()){
            return new ResponseEntity<>("Fornecedor não encontrado", HttpStatus.NOT_FOUND);
        }

        service.excluir(Fornecedor.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody Fornecedor FornecedorAtualizado){

        Optional<Fornecedor> FornecedorExistente = service.findById(id);

        if(!service.findById(id).isPresent()){
            return new ResponseEntity<>("Fornecedor não encontrado",HttpStatus.NOT_FOUND);
        }

        Fornecedor Fornecedor = FornecedorExistente.get();
        Fornecedor.setRazaoSocial(FornecedorAtualizado.getRazaoSocial());
        Fornecedor.setCnpj(FornecedorAtualizado.getCnpj());
        Fornecedor.setEmail(FornecedorAtualizado.getEmail());
        Fornecedor.setTelefone(FornecedorAtualizado.getTelefone());
        Fornecedor.setEndereco(FornecedorAtualizado.getEndereco());
        Fornecedor.setDescricao((FornecedorAtualizado.getDescricao()));

        service.save(Fornecedor);
        return ResponseEntity.ok(Fornecedor);
    }

    public Fornecedor converter(FornecedorDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Fornecedor fornecedor = modelMapper.map(dto, Fornecedor.class);
        Endereco endereco = modelMapper.map(dto,Endereco.class);
        fornecedor.setEndereco(endereco);

        return fornecedor;
    }



}
