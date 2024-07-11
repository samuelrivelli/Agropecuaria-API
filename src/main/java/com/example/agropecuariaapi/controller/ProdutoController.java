package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.dto.ProdutoDTO;
import com.example.agropecuariaapi.model.entity.Produto;
import com.example.agropecuariaapi.service.ProdutoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @GetMapping
    public ResponseEntity findAll() {
        List<Produto> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id){
        Optional Produto = service.findById(id);

        if(!Produto.isPresent()){
            return new ResponseEntity<>("Produto não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(Produto);

    }

    @PostMapping
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
    public ResponseEntity excluir(@PathVariable("id") Long id){
        Optional<Produto> Produto = service.findById(id);

        if(!Produto.isPresent()){
            return new ResponseEntity<>("Produto não encontrado", HttpStatus.NOT_FOUND);
        }

        service.excluir(Produto.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
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
