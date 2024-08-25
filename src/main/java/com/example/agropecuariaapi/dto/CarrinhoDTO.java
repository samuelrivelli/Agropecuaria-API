package com.example.agropecuariaapi.dto;

import com.example.agropecuariaapi.model.entity.Carrinho;
import com.example.agropecuariaapi.model.entity.Produto;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoDTO {
    private List<ProdutoDTO> produtos = new ArrayList<>();

    public static CarrinhoDTO create(Carrinho carrinho) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(carrinho, CarrinhoDTO.class);
    }

}
