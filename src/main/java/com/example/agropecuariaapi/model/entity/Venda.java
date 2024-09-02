package com.example.agropecuariaapi.model.entity;

import com.example.agropecuariaapi.views.Views;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private String formaDePagamento;
    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    @JsonIgnore
    private List<VendaProduto> vendaProdutos = new ArrayList<>();

    @Column(name = "valor_total")
    private Double valorTotal;

    @PrePersist
    @PreUpdate
    private void calcularValorTotal() {
        this.valorTotal = vendaProdutos.stream()
                .mapToDouble(vendaProduto -> vendaProduto.getProduto().getPreco() * vendaProduto.getQuantidade())
                .sum();
    }

    @JsonIgnore
    public List<VendaProduto> getVendaProdutos() {
        return vendaProdutos;
    }


}
