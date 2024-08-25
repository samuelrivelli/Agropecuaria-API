package com.example.agropecuariaapi.controller;

import com.example.agropecuariaapi.dto.VendaDTO;
import com.example.agropecuariaapi.exceptions.RegraNegocioException;
import com.example.agropecuariaapi.model.entity.Cliente;
import com.example.agropecuariaapi.model.entity.Produto;
import com.example.agropecuariaapi.model.entity.Venda;
import com.example.agropecuariaapi.model.entity.VendaProduto;
import com.example.agropecuariaapi.model.repository.VendaRepository;
import com.example.agropecuariaapi.service.ClienteService;
import com.example.agropecuariaapi.service.ProdutoService;
import com.example.agropecuariaapi.service.VendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    private ClienteService clienteService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private VendaRepository repository;

    @GetMapping
    @Operation(
            summary = "Mostra todas as vendas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendas encontradas"),
            @ApiResponse(responseCode = "404", description = "Vendas não encontradas")
    })
    public ResponseEntity findAll() {
        List<Venda> list = service.findAll();
        return ResponseEntity.ok(list.stream().map(VendaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Mostra uma única venda baseado no ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venda encontrada"),
            @ApiResponse(responseCode = "404", description = "Venda não encontrado")
    })

    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        Optional<Venda> venda = repository.findByIdWithProdutos(id);

        if (venda.isEmpty()) {
            return new ResponseEntity<>("Venda não encontrada", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(VendaDTO.create(venda.get()));
    }


    @PostMapping
    @Operation(
            summary = "Salva uma venda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Venda salva"),
            @ApiResponse(responseCode = "400", description = "Erro ao salvar venda")
    })
    public ResponseEntity<VendaDTO> createVenda(@RequestBody VendaDTO vendaDTO) {
        Venda venda = converter(vendaDTO);


        Optional<Cliente> clienteOpt = clienteService.findById(vendaDTO.getClienteId());
        if (clienteOpt.isPresent()) {
            venda.setCliente(clienteOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Venda novaVenda = service.save(venda);

        VendaDTO novaVendaDTO = VendaDTO.create(novaVenda);
        return new ResponseEntity<>(novaVendaDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualiza uma venda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venda atualizada"),
            @ApiResponse(responseCode = "404", description = "Venda não encontrada")
    })
    public ResponseEntity<VendaDTO> updateVenda(@PathVariable Long id, @RequestBody VendaDTO vendaDTO) throws RegraNegocioException {
        Venda venda = converter(vendaDTO);
        venda.setId(id);
        Venda updatedVenda = service.update(venda);
        return ResponseEntity.ok(VendaDTO.create(updatedVenda));
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "Exclui uma venda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Venda excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Venda não encontrado")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id){
        Optional<Venda> venda = service.findById(id);

        if(!venda.isPresent()){
            return new ResponseEntity<>("Venda não encontrada", HttpStatus.NOT_FOUND);
        }

        service.excluir(venda.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Venda converter(VendaDTO dto) {
        Venda venda = new Venda();
        venda.setId(dto.getId());
        venda.setFormaDePagamento(dto.getFormaDePagamento());

        Cliente cliente = clienteService.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        venda.setCliente(cliente);

        List<VendaProduto> vendaProdutos = dto.getProdutos().stream()
                .map(produtoDTO -> {
                    Produto produto = produtoService.findById(produtoDTO.getId())
                            .orElseThrow(() -> new RegraNegocioException("Produto não encontrado"));
                    VendaProduto vendaProduto = new VendaProduto();
                    vendaProduto.setProduto(produto);
                    vendaProduto.setQuantidade(produtoDTO.getQuantidade());
                    return vendaProduto;
                }).collect(Collectors.toList());

        venda.setVendaProdutos(vendaProdutos);

        return venda;
    }

}
