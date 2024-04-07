package com.example.agropecuariaapi.dto;

import com.example.agropecuariaapi.model.entity.HistoricoVendas;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoVendasDTO {

    private Long id;

    List<ProdutoDTO> produtos;

    public static HistoricoVendasDTO create(HistoricoVendas HistoricoVendas){
        ModelMapper modelMapper = new ModelMapper();

        HistoricoVendasDTO dto = modelMapper.map(HistoricoVendas, HistoricoVendasDTO.class);

        List<ProdutoDTO> produtosDTO = HistoricoVendas.getProdutos().stream().map(ProdutoDTO::create).collect(Collectors.toList());
        dto.setProdutos(produtosDTO);

        return dto;
    }
}
