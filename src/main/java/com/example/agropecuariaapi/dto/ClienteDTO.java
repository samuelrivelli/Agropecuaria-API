package com.example.agropecuariaapi.dto;

import com.example.agropecuariaapi.model.entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

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

    public static ClienteDTO create(Cliente cliente) {
        ModelMapper modelMapper = new ModelMapper();
        ClienteDTO dto = modelMapper.map(cliente, ClienteDTO.class);
        if (cliente.getEndereco() != null) {
            dto.setLogradouro(cliente.getEndereco().getLogradouro());
            dto.setNumero(cliente.getEndereco().getNumero());
            dto.setComplemento(cliente.getEndereco().getComplemento());
            dto.setBairro(cliente.getEndereco().getBairro());
            dto.setCidade(cliente.getEndereco().getCidade());
            dto.setUf(cliente.getEndereco().getUf());
            dto.setCep(cliente.getEndereco().getCep());
        }
        return dto;
    }
}
