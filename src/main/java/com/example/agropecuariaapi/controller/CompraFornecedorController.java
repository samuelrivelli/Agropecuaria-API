package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.dto.CompraFornecedorDTO;
import com.example.agropecuariaapi.model.entity.CompraFornecedor;
import com.example.agropecuariaapi.model.entity.Endereco;
import com.example.agropecuariaapi.model.entity.Fornecedor;
import com.example.agropecuariaapi.model.repository.CompraFornecedorRepository;
import com.example.agropecuariaapi.service.CompraFornecedorService;
import com.example.agropecuariaapi.service.FornecedorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/compraFornecedor")
public class CompraFornecedorController {

    @Autowired
    private CompraFornecedorService service;

    @Autowired
    private CompraFornecedorService compraFornecedorService;

    @Autowired
    private FornecedorService fornecedorService;

    @GetMapping
    public ResponseEntity findAll() {
        List<CompraFornecedor> list = service.findAll();
        return ResponseEntity.ok(list.stream().map(CompraFornecedorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id){
        Optional<CompraFornecedor> compraFornecedor = service.findById(id);

        if(!compraFornecedor.isPresent()){
            return new ResponseEntity<>("Compra não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(compraFornecedor.map(CompraFornecedorDTO::create));

    }

    @PostMapping
    public ResponseEntity<CompraFornecedorDTO> createCompraFornecedor(@RequestBody CompraFornecedorDTO compraFornecedorDTO) {
        CompraFornecedor compraFornecedor = converter(compraFornecedorDTO);

        Fornecedor fornecedor = compraFornecedor.getFornecedor();
        fornecedor = fornecedorService.save(fornecedor);

        compraFornecedor.setFornecedor(fornecedor);

        compraFornecedor = compraFornecedorService.save(compraFornecedor);

        CompraFornecedorDTO dto = CompraFornecedorDTO.create(compraFornecedor);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id){
        Optional<CompraFornecedor> CompraFornecedor = service.findById(id);

        if(!CompraFornecedor.isPresent()){
            return new ResponseEntity<>("Compra não encontrada", HttpStatus.NOT_FOUND);
        }

        service.excluir(CompraFornecedor.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private CompraFornecedor converter(CompraFornecedorDTO dto) {

        ModelMapper modelMapper = new ModelMapper();

        CompraFornecedor compraFornecedor = modelMapper.map(dto, CompraFornecedor.class);

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setRazaoSocial(dto.getRazaoSocial());
        fornecedor.setCnpj(dto.getCnpj());
        fornecedor.setTelefone(dto.getTelefone());
        fornecedor.setEmail(dto.getEmail());
        fornecedor.setDescricao(dto.getDescricao());

        Endereco endereco = new Endereco();
        endereco.setLogradouro(dto.getLogradouro());
        endereco.setNumero(dto.getNumero());
        endereco.setComplemento(dto.getComplemento());
        endereco.setBairro(dto.getBairro());
        endereco.setCidade(dto.getCidade());
        endereco.setUf(dto.getUf());
        endereco.setCep(dto.getCep());

        fornecedor.setEndereco(endereco);
        compraFornecedor.setFornecedor(fornecedor);

        return compraFornecedor;
    }



}
