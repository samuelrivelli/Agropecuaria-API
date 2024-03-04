package com.example.agropecuariaapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String razaoSocial;
    private String cnpj;
    private String telefone;
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;

    private String descricao;
}
