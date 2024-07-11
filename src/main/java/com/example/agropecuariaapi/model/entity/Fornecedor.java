package com.example.agropecuariaapi.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Setter
@Getter
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
