package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.dto.CarrinhoDTO;
import com.example.agropecuariaapi.dto.ClienteDTO;
import com.example.agropecuariaapi.model.entity.Carrinho;
import com.example.agropecuariaapi.model.entity.Produto;
import com.example.agropecuariaapi.model.repository.ProdutoRepository;
import com.example.agropecuariaapi.service.CarrinhoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carrinhos")
public class CarrinhoController {

    @Autowired
    private CarrinhoService service;

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    @Operation(
            summary = "Mostra todos os carrinhos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrinhos encontrados"),
            @ApiResponse(responseCode = "404", description = "Carrinhos não encontrados")
    })
    public ResponseEntity<List<Carrinho>> findAll() {
        List<Carrinho> carrinhos = service.findAll();
        return ResponseEntity.ok(carrinhos);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Mostra um único carrinho baseado no seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrinho encontrado"),
            @ApiResponse(responseCode = "404", description = "Carrinho não encontrado")
    })
    public ResponseEntity<Carrinho> findById(@PathVariable Long id) {
        Carrinho carrinho = service.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado com ID: " + id));
        return ResponseEntity.ok(carrinho);
    }

    @PostMapping
    @Operation(
            summary = "Salva um carrinho")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Carrinho salvo"),
            @ApiResponse(responseCode = "400", description = "Erro ao salvar carrinho")
    })
    public ResponseEntity<Carrinho> criarCarrinho(@RequestBody Carrinho carrinho) {
        List<Produto> produtos = carrinho.getProdutos().stream()
                .map(produto -> produtoRepository.findById(produto.getId())
                        .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + produto.getId())))
                .collect(Collectors.toList());

        carrinho.setProdutos(produtos);

        Carrinho novoCarrinho = service.save(carrinho);

        return ResponseEntity.ok(novoCarrinho);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Exclui um carrinho")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Carrinho excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Carrinho não encontrado")
    })
    public ResponseEntity delete(@PathVariable("id") Long id){
        Optional<Carrinho> carrinho = service.findById(id);
        if(!carrinho.isPresent()){
            return new ResponseEntity<>("Carrinho não encontrado", HttpStatus.NOT_FOUND);
        }

        service.excluir(carrinho.get());

        return ResponseEntity.noContent().build();

    }

    @Operation(
            summary = "Atualiza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrinhoo atualizado"),
            @ApiResponse(responseCode = "404", description = "Carrinho não encontrado")
    })

    @PutMapping("/{id}")
    public ResponseEntity<Carrinho> update(@PathVariable Long id, @RequestBody Carrinho carrinhoAtualizado) {
        Carrinho carrinhoExistente = service.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado com ID: " + id));


        List<Produto> produtos = carrinhoAtualizado.getProdutos().stream()
                .map(produto -> produtoRepository.findById(produto.getId())
                        .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + produto.getId())))
                .collect(Collectors.toList());


        carrinhoExistente.setProdutos(produtos);

        Carrinho carrinhoSalvo = service.save(carrinhoExistente);
        return ResponseEntity.ok(carrinhoSalvo);
    }



}
