package com.example.agropecuariaapi.dto;

import com.example.agropecuariaapi.model.entity.Venda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaDTO {

    private Long id;
    private Long clienteId;
    private String nomeCliente;
    private String formaDePagamento;
    private List<ProdutoDTO> produtos = new ArrayList<>();

    public static VendaDTO create(Venda venda) {
        ModelMapper modelMapper = new ModelMapper();
        VendaDTO vendaDTO = modelMapper.map(venda, VendaDTO.class);

        if (venda.getCliente() != null) {
            vendaDTO.setClienteId(venda.getCliente().getId());
        } else {
            vendaDTO.setClienteId(null);
        }

        vendaDTO.setProdutos(
                venda.getVendaProdutos().stream()
                        .map(vendaProduto -> {
                            ProdutoDTO produtoDTO = modelMapper.map(vendaProduto.getProduto(), ProdutoDTO.class);
                            produtoDTO.setQuantidade(vendaProduto.getQuantidade()); // Define a quantidade do produto
                            return produtoDTO;
                        })
                        .collect(Collectors.toList())
        );

        return vendaDTO;
    }
}
