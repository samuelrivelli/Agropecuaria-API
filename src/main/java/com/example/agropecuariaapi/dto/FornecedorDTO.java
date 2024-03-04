package com.example.agropecuariaapi.dto;

import com.example.agropecuariaapi.model.entity.Fornecedor;
import com.example.agropecuariaapi.model.entity.Endereco;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FornecedorDTO {
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

    public static FornecedorDTO create(Fornecedor Fornecedor) {
        ModelMapper modelMapper = new ModelMapper();

        FornecedorDTO dto = null;
        if (Fornecedor.getEndereco() != null) {
            dto = modelMapper.map(Fornecedor, FornecedorDTO.class);
            dto.logradouro = Fornecedor.getEndereco().getLogradouro();
            dto.numero = Fornecedor.getEndereco().getNumero();
            dto.complemento = Fornecedor.getEndereco().getComplemento();
            dto.bairro = Fornecedor.getEndereco().getBairro();
            dto.cidade = Fornecedor.getEndereco().getCidade();
            dto.uf = Fornecedor.getEndereco().getUf();
            dto.cep = Fornecedor.getEndereco().getCep();
        }
        return dto;
    }
}
