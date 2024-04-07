package com.example.agropecuariaapi.dto;

import com.example.agropecuariaapi.model.entity.Estoque;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstoqueDTO {

    private Long id;

    List<ProdutoDTO> produtos;

    public static EstoqueDTO create(Estoque estoque){
        ModelMapper modelMapper = new ModelMapper();

        EstoqueDTO dto = modelMapper.map(estoque, EstoqueDTO.class);

        List<ProdutoDTO> produtosDTO = estoque.getProdutos().stream().map(ProdutoDTO::create).collect(Collectors.toList());
        dto.setProdutos(produtosDTO);

        return dto;
    }
}
