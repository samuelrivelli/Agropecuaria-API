package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.dto.VendaDTO;
import com.example.agropecuariaapi.model.entity.Produto;
import com.example.agropecuariaapi.model.entity.Venda;
import com.example.agropecuariaapi.model.entity.VendaProduto;
import com.example.agropecuariaapi.service.ProdutoService;
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

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity findAll() {
        List<Venda> list = service.findAll();
        return ResponseEntity.ok(list.stream().map(VendaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id){
        Optional<Venda> venda = service.findById(id);

        if(!venda.isPresent()){
            return new ResponseEntity<>("Venda não encontrada", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(venda.map(VendaDTO::create));
    }

    @PostMapping
    public ResponseEntity<VendaDTO> createVenda(@RequestBody VendaDTO vendaDTO) {
        Venda venda = converter(vendaDTO);
        Venda savedVenda = service.save(venda);
        return ResponseEntity.ok(VendaDTO.create(savedVenda));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendaDTO> updateVenda(@PathVariable Long id, @RequestBody VendaDTO vendaDTO) throws Exception {
        Venda venda = converter(vendaDTO);
        venda.setId(id);
        Venda updatedVenda = service.update(venda);
        return ResponseEntity.ok(VendaDTO.create(updatedVenda));
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id){
        Optional<Venda> venda = service.findById(id);

        if(!venda.isPresent()){
            return new ResponseEntity<>("Venda não encontrada", HttpStatus.NOT_FOUND);
        }

        service.excluir(venda.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Venda converter(VendaDTO dto) {
        Venda venda = modelMapper.map(dto, Venda.class);

        // Mapeamento para VendaProduto
        List<VendaProduto> vendaProdutos = dto.getProdutos().stream()
                .map(produtoDTO -> {
                    Produto produto = produtoService.findById(produtoDTO.getId())
                            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
                    VendaProduto vendaProduto = new VendaProduto();
                    vendaProduto.setProduto(produto);
                    vendaProduto.setQuantidade(produtoDTO.getQuantidade());  // Certifique-se de que quantidade não é nula
                    return vendaProduto;
                }).collect(Collectors.toList());

        venda.setVendaProdutos(vendaProdutos);

        return venda;
    }

}
