package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.dto.VendaDTO;
import com.example.agropecuariaapi.dto.VendaDTO;
import com.example.agropecuariaapi.model.entity.Venda;
import com.example.agropecuariaapi.model.entity.Produto;
import com.example.agropecuariaapi.model.entity.Venda;
import com.example.agropecuariaapi.service.VendaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    private VendaService service;

    @GetMapping
    public ResponseEntity findAll() {
        List<Venda> list = service.findAll();
        return ResponseEntity.ok(list.stream().map(VendaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id){
        Optional<Venda > Venda = service.findById(id);

        if(!Venda.isPresent()){
            return new ResponseEntity<>("Venda não encontrada", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(Venda.map(VendaDTO::create));

    }
    @PostMapping()
    public ResponseEntity post(@RequestBody VendaDTO dto) {
        try {
            Venda Venda = converter(dto);
            Venda = service.save(Venda);
            return new ResponseEntity(Venda, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody VendaDTO dto) {
        if (!service.findById(id).isPresent()) {
            return new ResponseEntity("Venda não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Venda Venda = converter(dto);
            Venda.setId(id);
            service.save(Venda);
            return ResponseEntity.ok(Venda);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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

    private Venda converter(VendaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Venda Venda= modelMapper.map(dto, Venda.class);

        List<Produto> produtos = dto.getProdutos().stream()
                .map(produtoDTO -> modelMapper.map(produtoDTO, Produto.class))
                .collect(Collectors.toList());

        Venda.setProdutos(produtos);

        return Venda;
    }


}
