package com.example.agropecuariaapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
