package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.dto.EstoqueDTO;
import com.example.agropecuariaapi.dto.ProdutoDTO;
import com.example.agropecuariaapi.model.entity.Estoque;
import com.example.agropecuariaapi.model.entity.Produto;
import com.example.agropecuariaapi.service.EstoqueService;
import com.example.agropecuariaapi.service.ProdutoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/estoques")
public class EstoqueController {

    @Autowired
    private EstoqueService service;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity findAll() {
        List<Estoque> list = service.findAll();
        return ResponseEntity.ok(list.stream().map(EstoqueDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id){
        Optional<Estoque> estoque = service.findById(id);

        if(!estoque.isPresent()){
            return new ResponseEntity<>("Estoque não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(estoque.map(EstoqueDTO::create));

    }
    @PostMapping
    public ResponseEntity<EstoqueDTO> createEstoque(@RequestBody EstoqueDTO estoqueDTO) {
        Estoque estoque = converter(estoqueDTO);

        for (Produto produto : estoque.getProdutos()) {
            produtoService.save(produto);
        }
        estoque = service.save(estoque);

        EstoqueDTO dto = EstoqueDTO.create(estoque);
        return ResponseEntity.ok(dto);
    }




    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id){
        Optional<Estoque> Estoque = service.findById(id);

        if(!Estoque.isPresent()){
            return new ResponseEntity<>("Estoque não encontrado", HttpStatus.NOT_FOUND);
        }

        service.excluir(Estoque.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Estoque converter(EstoqueDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Estoque estoque = modelMapper.map(dto, Estoque.class);

        List<Produto> produtos = dto.getProdutos().stream()
                .map(produtoDTO -> modelMapper.map(produtoDTO, Produto.class))
                .collect(Collectors.toList());

        estoque.setProdutos(produtos);

        return estoque;
    }


}
