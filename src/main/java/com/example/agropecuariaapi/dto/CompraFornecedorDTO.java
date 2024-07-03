package com.example.agropecuariaapi.dto;

import com.example.agropecuariaapi.model.entity.CompraFornecedor;
import org.modelmapper.ModelMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

        if (compraFornecedor.getFornecedor() != null) {
            dto.razaoSocial = compraFornecedor.getFornecedor().getRazaoSocial();
            dto.cnpj = compraFornecedor.getFornecedor().getCnpj();
            dto.telefone = compraFornecedor.getFornecedor().getTelefone();
            dto.email = compraFornecedor.getFornecedor().getEmail();

            if (compraFornecedor.getFornecedor().getEndereco() != null) {
                dto.logradouro = compraFornecedor.getFornecedor().getEndereco().getLogradouro();
                dto.numero = compraFornecedor.getFornecedor().getEndereco().getNumero();
                dto.complemento = compraFornecedor.getFornecedor().getEndereco().getComplemento();
                dto.bairro = compraFornecedor.getFornecedor().getEndereco().getBairro();
                dto.cidade = compraFornecedor.getFornecedor().getEndereco().getCidade();
                dto.uf = compraFornecedor.getFornecedor().getEndereco().getUf();
                dto.cep = compraFornecedor.getFornecedor().getEndereco().getCep();
            } else {
                dto.logradouro = null;
                dto.numero = null;
                dto.complemento = null;
                dto.bairro = null;
                dto.cidade = null;
                dto.uf = null;
                dto.cep = null;
            }

            dto.descricao = compraFornecedor.getFornecedor().getDescricao();
        } else {
            dto.razaoSocial = null;
            dto.cnpj = null;
            dto.telefone = null;
            dto.email = null;
            dto.logradouro = null;
            dto.numero = null;
            dto.complemento = null;
            dto.bairro = null;
            dto.cidade = null;
            dto.uf = null;
            dto.cep = null;
            dto.descricao = null;
        }

        return dto;
    }
}
