package com.example.agropecuariaapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Cliente cliente;

    private String formaDePagamento;

   @ManyToMany
   @JoinTable(name = "venda_produtos",
           joinColumns = @JoinColumn(name = "venda_id"),
           inverseJoinColumns = @JoinColumn(name = "produto_id"))
    private List<Produto> produtos;

}
