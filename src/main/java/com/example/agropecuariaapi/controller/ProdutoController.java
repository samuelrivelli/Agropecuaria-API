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
    public ResponseEntity post(@RequestBody Produto Produto){
        Produto = service.salvar(Produto);
        return ResponseEntity.ok().body(Produto);
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
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody Produto ProdutoAtualizado){

        Optional<Produto> ProdutoExistente = service.findById(id);

        if(!service.findById(id).isPresent()){
            return new ResponseEntity<>("Produtos não encontrado",HttpStatus.NOT_FOUND);
        }

        Produto Produto = ProdutoExistente.get();
        Produto.setNome(ProdutoAtualizado.getNome());
        Produto.setTamanho(ProdutoAtualizado.getTamanho());
        Produto.setLote(ProdutoAtualizado.getLote());
        Produto.setValidade(ProdutoAtualizado.getValidade());
        Produto.setQuantidadeEmEstoque(ProdutoAtualizado.getQuantidadeEmEstoque());
        Produto.setEstoqueMinimo(ProdutoAtualizado.getEstoqueMinimo());
        Produto.setEstoqueMaximo(ProdutoAtualizado.getEstoqueMaximo());
        Produto.setValorDeReposicao(ProdutoAtualizado.getValorDeReposicao());
        Produto.setPreco(ProdutoAtualizado.getPreco());

        service.salvar(Produto);
        return ResponseEntity.ok(Produto);
    }

    public Produto converter(ProdutoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Produto.class);
    }

}
