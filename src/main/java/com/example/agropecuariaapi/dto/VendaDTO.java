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
    private Double valorTotal;

    public static VendaDTO create(Venda venda) {
        VendaDTO dto = new VendaDTO();
        dto.setId(venda.getId());
        dto.setFormaDePagamento(venda.getFormaDePagamento());

        if (venda.getCliente() != null) {
            dto.setClienteId(venda.getCliente().getId());
            dto.setNomeCliente(venda.getCliente().getNome());
        } else {
            dto.setClienteId(null);
            dto.setNomeCliente(null);
        }

        dto.setProdutos(venda.getVendaProdutos().stream()
                .map(vendaProduto -> {
                    ProdutoDTO produtoDTO = new ProdutoDTO();
                    produtoDTO.setId(vendaProduto.getProduto().getId());
                    produtoDTO.setNome(vendaProduto.getProduto().getNome());
                    produtoDTO.setQuantidade(vendaProduto.getQuantidade());
                    produtoDTO.setLote(vendaProduto.getProduto().getLote());
                    produtoDTO.setEstoqueMaximo(vendaProduto.getProduto().getEstoqueMaximo());
                    produtoDTO.setEstoqueMinimo(vendaProduto.getProduto().getEstoqueMinimo());
                    produtoDTO.setPreco(vendaProduto.getProduto().getPreco());
                    produtoDTO.setQuantidadeEmEstoque(vendaProduto.getProduto().getQuantidadeEmEstoque());
                    produtoDTO.setValidade(vendaProduto.getProduto().getValidade());
                    produtoDTO.setValorDeReposicao(vendaProduto.getProduto().getValorDeReposicao());
                    return produtoDTO;
                }).collect(Collectors.toList())
        );

        double valorTotal = venda.getVendaProdutos().stream()
                .mapToDouble(vendaProduto -> vendaProduto.getProduto().getPreco() * vendaProduto.getQuantidade())
                .sum();
        dto.setValorTotal(valorTotal);

        return dto;
    }

}
