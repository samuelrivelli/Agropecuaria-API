package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.dto.HistoricoVendasDTO;
import com.example.agropecuariaapi.model.entity.HistoricoVendas;
import com.example.agropecuariaapi.model.entity.Produto;
import com.example.agropecuariaapi.service.HistoricoVendasService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/historicoVendas")
public class HistoricoVendasController {

    @Autowired
    private HistoricoVendasService service;

    @GetMapping
    public ResponseEntity findAll() {
        List<HistoricoVendas> list = service.findAll();
        return ResponseEntity.ok(list.stream().map(HistoricoVendasDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id){
        Optional<HistoricoVendas> HistoricoVendas = service.findById(id);

        if(!HistoricoVendas.isPresent()){
            return new ResponseEntity<>("Historico de vendas não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(HistoricoVendas.map(HistoricoVendasDTO::create));

    }

    @PostMapping
    public ResponseEntity<HistoricoVendasDTO> createHistoricoVendas(@RequestBody HistoricoVendasDTO historicoVendasDTO) {

        HistoricoVendas historicoVendas = converter(historicoVendasDTO);

        HistoricoVendas novoHistorico = service.save(historicoVendas);

        HistoricoVendasDTO novoHistoricoDTO = HistoricoVendasDTO.create(novoHistorico);
        return new ResponseEntity<>(novoHistoricoDTO, HttpStatus.CREATED);
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

    private HistoricoVendas converter(HistoricoVendasDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        HistoricoVendas historicoVendas= modelMapper.map(dto, HistoricoVendas.class);

        List<Produto> produtos = dto.getProdutos().stream()
                .map(produtoDTO -> modelMapper.map(produtoDTO, Produto.class))
                .collect(Collectors.toList());

        historicoVendas.setProdutos(produtos);

        return historicoVendas;
    }



}
