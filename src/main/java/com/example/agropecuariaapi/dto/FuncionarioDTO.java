package com.example.agropecuariaapi.dto;

import com.example.agropecuariaapi.model.entity.Funcionario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String logradouro;
    private Integer numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;

    public static FuncionarioDTO create(Funcionario Funcionario) {
        ModelMapper modelMapper = new ModelMapper();

        FuncionarioDTO dto = null;
        if (Funcionario.getEndereco() != null) {
            dto = modelMapper.map(Funcionario, FuncionarioDTO.class);
            dto.logradouro = Funcionario.getEndereco().getLogradouro();
            dto.numero = Funcionario.getEndereco().getNumero();
            dto.complemento = Funcionario.getEndereco().getComplemento();
            dto.bairro = Funcionario.getEndereco().getBairro();
            dto.cidade = Funcionario.getEndereco().getCidade();
            dto.uf = Funcionario.getEndereco().getUf();
            dto.cep = Funcionario.getEndereco().getCep();
        }
        return dto;
    }

}
