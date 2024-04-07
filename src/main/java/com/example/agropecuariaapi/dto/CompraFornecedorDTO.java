package com.example.agropecuariaapi.dto;

import com.example.agropecuariaapi.model.entity.CompraFornecedor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraFornecedorDTO {
    private Long id;
    private String razaoSocial;
    private String cnpj;
    private String telefone;
    private String email;
    private String logradouro;
    private Integer numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
    private String descricao;

    public static CompraFornecedorDTO create(CompraFornecedor compraFornecedor) {
        ModelMapper modelMapper = new ModelMapper();
        CompraFornecedorDTO dto = modelMapper.map(compraFornecedor, CompraFornecedorDTO.class);
        dto.razaoSocial = compraFornecedor.getFornecedor().getRazaoSocial();
        dto.cnpj = compraFornecedor.getFornecedor().getCnpj();
        dto.telefone = compraFornecedor.getFornecedor().getTelefone();
        dto.email = compraFornecedor.getFornecedor().getEmail();
        dto.logradouro = compraFornecedor.getFornecedor().getEndereco().getLogradouro();
        dto.numero = compraFornecedor.getFornecedor().getEndereco().getNumero();
        dto.complemento = compraFornecedor.getFornecedor().getEndereco().getComplemento();
        dto.bairro = compraFornecedor.getFornecedor().getEndereco().getBairro();
        dto.cidade = compraFornecedor.getFornecedor().getEndereco().getCidade();
        dto.uf = compraFornecedor.getFornecedor().getEndereco().getUf();
        dto.cep = compraFornecedor.getFornecedor().getEndereco().getCep();
        dto.descricao = compraFornecedor.getFornecedor().getDescricao();

        return dto;
    }
}
