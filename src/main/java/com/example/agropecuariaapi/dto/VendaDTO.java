package com.example.agropecuariaapi.dto;

import com.example.agropecuariaapi.model.entity.Venda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaDTO {

    private Long id;

    List<ProdutoDTO> produtos;

    public static VendaDTO create(Venda Venda){
        ModelMapper modelMapper = new ModelMapper();

        VendaDTO dto = modelMapper.map(Venda, VendaDTO.class);

        List<ProdutoDTO> produtosDTO = Venda.getProdutos().stream().map(ProdutoDTO::create).collect(Collectors.toList());
        dto.setProdutos(produtosDTO);

        return dto;
    }
}
