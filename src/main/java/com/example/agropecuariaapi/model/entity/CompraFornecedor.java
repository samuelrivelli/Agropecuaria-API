package com.example.agropecuariaapi.model.entity;

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
@Table(name = "compra_fornecedor")
public class CompraFornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Fornecedor fornecedor;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate data;
    private Double valor;
    private String notaFiscal;

    @JsonIgnore
    @ManyToMany
    private List<Produto> produtos;

}
