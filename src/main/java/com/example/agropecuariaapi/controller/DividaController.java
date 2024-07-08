package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.dto.ClienteDTO;
import com.example.agropecuariaapi.dto.DividaDTO;
import com.example.agropecuariaapi.model.entity.Cliente;
import com.example.agropecuariaapi.model.entity.Divida;
import com.example.agropecuariaapi.service.ClienteService;
import com.example.agropecuariaapi.service.DividasService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dividas")
public class DividaController {

    @Autowired
    private DividasService service;

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity findAll() {
        List<Divida> list = service.findAll();
        return ResponseEntity.ok(list.stream().map(DividaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id){
        Optional<Divida> Divida = service.findById(id);

        if(!Divida.isPresent()){
            return new ResponseEntity<>("Dividas não encontrado", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(Divida.map(DividaDTO::create));


    }
    @PostMapping
    public ResponseEntity<DividaDTO> createDivida(@RequestBody DividaDTO dividaDTO) {
        Divida divida = converter(dividaDTO);

        // Salvar o cliente primeiro
        Cliente cliente = divida.getCliente();
        cliente = clienteService.save(cliente);

        // Associar o cliente salvo à divida
        divida.setCliente(cliente);

        // Salvar a divida
        divida = service.save(divida);

        DividaDTO dto = DividaDTO.create(divida);
        return ResponseEntity.ok(dto);
    }


    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id){
        Optional<Divida> Dividas = service.findById(id);

        if(!Dividas.isPresent()){
            return new ResponseEntity<>("Dividas não encontrado", HttpStatus.NOT_FOUND);
        }

        service.excluir(Dividas.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody Divida dividaAtualizado){

        Optional<Divida> DividasExistente = service.findById(id);

        if(!service.findById(id).isPresent()){
            return new ResponseEntity<>("Dividas não encontrado",HttpStatus.NOT_FOUND);
        }

        Divida Divida = DividasExistente.get();
        Divida.setCliente(dividaAtualizado.getCliente());
        Divida.setValor(dividaAtualizado.getValor());
        Divida.setVencimento(dividaAtualizado.getVencimento());
        service.save(Divida);
        return ResponseEntity.ok(Divida);
    }

    private Divida converter(DividaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Divida divida = modelMapper.map(dto, Divida.class);

        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setCpf(dto.getCpf());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefone(dto.getTelefone());

        divida.setCliente(cliente);
        divida.setValor(dto.getValor());
        divida.setVencimento(dto.getVencimento());

        return divida;
    }

}
