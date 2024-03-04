package com.example.agropecuariaapi.dto;

import com.example.agropecuariaapi.model.entity.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {
    private Long id;

    private String nome;
    private Double tamanho;
    private String lote;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate validade;
    private Integer quantidadeEmEstoque;
    private Integer estoqueMinimo;
    private Integer estoqueMaximo;
    private Integer valorDeReposicao;
    private Double preco;

    public static ProdutoDTO create(Produto produto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(produto, ProdutoDTO.class);
    }
}

