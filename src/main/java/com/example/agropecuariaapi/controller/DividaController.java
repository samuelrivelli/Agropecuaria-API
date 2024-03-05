package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.dto.ClienteDTO;
import com.example.agropecuariaapi.dto.DividaDTO;
import com.example.agropecuariaapi.model.entity.Divida;
import com.example.agropecuariaapi.service.DividasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dividas")
public class DividaController {

    @Autowired
    private DividasService service;

    @GetMapping
    public ResponseEntity findAll() {
        List<Divida> list = service.findAll();
        return ResponseEntity.ok(list.stream().map(DividaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id){
        Optional<Divida> Divida = service.findById(id);

        if(!Divida.isPresent()){
            return new ResponseEntity<>("Dividas não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(Divida.map(DividaDTO::create));


    }
    @PostMapping
    public ResponseEntity post(@RequestBody Divida Divida){
        Divida = service.salvar(Divida);
        return ResponseEntity.ok().body(Divida);
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id){
        Optional<Divida> Dividas = service.findById(id);

        if(!Dividas.isPresent()){
            return new ResponseEntity<>("Dividas não encontrado", HttpStatus.NOT_FOUND);
        }

        service.excluir(Dividas.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody Divida dividaAtualizado){

        Optional<Divida> DividasExistente = service.findById(id);

        if(!service.findById(id).isPresent()){
            return new ResponseEntity<>("Dividas não encontrado",HttpStatus.NOT_FOUND);
        }

        Divida Divida = DividasExistente.get();
        Divida.setCliente(dividaAtualizado.getCliente());
        Divida.setValor(dividaAtualizado.getValor());
        Divida.setVencimento(dividaAtualizado.getVencimento());
        service.salvar(Divida);
        return ResponseEntity.ok(Divida);
    }

}
