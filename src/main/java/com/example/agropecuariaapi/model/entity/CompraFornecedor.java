package com.example.agropecuariaapi.model.entity;

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
public class CompraFornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Fornecedor fornecedor;
    @OneToMany
    private List<Produto> produtos;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate data;
    private Double valor;
    private String notaFiscal;

}
