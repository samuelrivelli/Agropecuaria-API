package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.model.entity.Dividas;
import com.example.agropecuariaapi.service.DividasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Dividass")
public class DividasController {

    @Autowired
    private DividasService service;

    @GetMapping
    public ResponseEntity findAll() {
        List<Dividas> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id){
        Optional<Dividas> Divida = service.findById(id);

        if(!Divida.isPresent()){
            return new ResponseEntity<>("Dividas não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(Divida);


    }
    @PostMapping
    public ResponseEntity post(@RequestBody Dividas Dividas){
        Dividas = service.salvar(Dividas);
        return ResponseEntity.ok().body(Dividas);
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id){
        Optional<Dividas> Dividas = service.findById(id);

        if(!Dividas.isPresent()){
            return new ResponseEntity<>("Dividas não encontrado", HttpStatus.NOT_FOUND);
        }

        service.excluir(Dividas.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody Dividas DividasAtualizado){

        Optional<Dividas> DividasExistente = service.findById(id);

        if(!service.findById(id).isPresent()){
            return new ResponseEntity<>("Dividas não encontrado",HttpStatus.NOT_FOUND);
        }

        Dividas Dividas = DividasExistente.get();
        Dividas.setCliente(DividasAtualizado.getCliente());
        Dividas.setValor(DividasAtualizado.getValor());
        Dividas.setVencimento(DividasAtualizado.getVencimento());
        service.salvar(Dividas);
        return ResponseEntity.ok(Dividas);
    }

}
