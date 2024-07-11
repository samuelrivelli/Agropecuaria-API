package com.example.agropecuariaapi.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String tamanho;
    private String lote;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate validade;

    private Integer quantidadeEmEstoque;
    private Integer estoqueMinimo;
    private Integer estoqueMaximo;
    private Integer valorDeReposicao;
    private Double preco;

    @ManyToOne
    private Estoque estoque;

    @ManyToOne
    private HistoricoVendas historicoVendas;

    @JsonIgnore
    @ManyToMany(mappedBy = "produtos")
    private List<Venda> vendas;


    @JsonIgnore
    @JsonBackReference
    @ManyToMany(mappedBy = "produtos")
    private List<CompraFornecedor> compraFornecedores;
}
