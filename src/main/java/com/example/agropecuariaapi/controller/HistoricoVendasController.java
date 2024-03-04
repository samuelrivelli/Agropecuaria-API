package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.model.entity.HistoricoVendas;
import com.example.agropecuariaapi.service.HistoricoVendasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/historicoVendass")
public class HistoricoVendasController {

    @Autowired
    private HistoricoVendasService service;

    @GetMapping
    public ResponseEntity findAll() {
        List<HistoricoVendas> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id){
        Optional HistoricoVendas = service.findById(id);

        if(!HistoricoVendas.isPresent()){
            return new ResponseEntity<>("Historico de vendas não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(HistoricoVendas);

    }

    @PostMapping
    public ResponseEntity post(@RequestBody HistoricoVendas HistoricoVendas){
        HistoricoVendas = service.salvar(HistoricoVendas);
        return ResponseEntity.ok().body(HistoricoVendas);
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id){
        Optional<HistoricoVendas> HistoricoVendas = service.findById(id);

        if(!HistoricoVendas.isPresent()){
            return new ResponseEntity<>("Historico de vendas não encontrado", HttpStatus.NOT_FOUND);
        }

        service.excluir(HistoricoVendas.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody HistoricoVendas HistoricoVendasAtualizado){

        Optional<HistoricoVendas> HistoricoVendasExistente = service.findById(id);

        if(!service.findById(id).isPresent()){
            return new ResponseEntity<>("HistoricoVendas não encontrado",HttpStatus.NOT_FOUND);
        }

        HistoricoVendas HistoricoVendas = HistoricoVendasExistente.get();
        HistoricoVendas.setVendas(HistoricoVendasAtualizado.getVendas());

        service.salvar(HistoricoVendas);
        return ResponseEntity.ok(HistoricoVendas);
    }


}
