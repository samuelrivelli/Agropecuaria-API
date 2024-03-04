package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.model.entity.Venda;
import com.example.agropecuariaapi.model.entity.Venda;
import com.example.agropecuariaapi.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Vendas")
public class VendaController {

    @Autowired
    private VendaService service;

    @GetMapping
    public ResponseEntity findAll() {
        List<Venda> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id){
        Optional Venda = service.findById(id);

        if(!Venda.isPresent()){
            return new ResponseEntity<>("Venda não encontrada", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(Venda);

    }
    @PostMapping
    public ResponseEntity post(@RequestBody Venda Venda){
        Venda = service.salvar(Venda);
        return ResponseEntity.ok().body(Venda);
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id){
        Optional<Venda> Venda = service.findById(id);

        if(!Venda.isPresent()){
            return new ResponseEntity<>("Venda não encontrada", HttpStatus.NOT_FOUND);
        }

        service.excluir(Venda.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody Venda VendaAtualizado){

        Optional<Venda> VendaExistente = service.findById(id);

        if(!service.findById(id).isPresent()){
            return new ResponseEntity<>("Venda não encontrado",HttpStatus.NOT_FOUND);
        }

        Venda Venda = VendaExistente.get();
        Venda.setProdutos(VendaAtualizado.getProdutos());
        Venda.setQuantidade(VendaAtualizado.getQuantidade());
        Venda.setCliente(VendaAtualizado.getCliente());
        Venda.setValidade(VendaAtualizado.getValidade());
        Venda.setFormaDePagamento(VendaAtualizado.getFormaDePagamento());

        service.salvar(Venda);
        return ResponseEntity.ok(Venda);
    }


}
