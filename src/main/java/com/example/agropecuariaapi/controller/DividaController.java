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

        Cliente cliente = divida.getCliente();
        cliente = clienteService.save(cliente);

        divida.setCliente(cliente);

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

    @PutMapping("/{id}")
    public ResponseEntity<DividaDTO> update(@PathVariable Long id, @RequestBody DividaDTO dividaDTO) {
        try {
            Optional<Divida> optionalDivida = service.findById(id);
            if (!optionalDivida.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Divida dividaExistente = optionalDivida.get();

            dividaExistente.setValor(dividaDTO.getValor());
            dividaExistente.setVencimento(dividaDTO.getVencimento());

            Cliente clienteExistente = dividaExistente.getCliente();
            clienteExistente.setNome(dividaDTO.getNome());
            clienteExistente.setCpf(dividaDTO.getCpf());
            clienteExistente.setEmail(dividaDTO.getEmail());
            clienteExistente.setTelefone(dividaDTO.getTelefone());

            clienteExistente = clienteService.save(clienteExistente);

            dividaExistente.setCliente(clienteExistente);

            dividaExistente = service.save(dividaExistente);

            DividaDTO dto = DividaDTO.create(dividaExistente);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
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
