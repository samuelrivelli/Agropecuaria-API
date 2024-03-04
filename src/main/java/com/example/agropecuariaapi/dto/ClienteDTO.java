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

        ClienteDTO dto = null;
        if (cliente.getEndereco() != null) {
            dto = modelMapper.map(cliente, ClienteDTO.class);
            dto.logradouro = cliente.getEndereco().getLogradouro();
            dto.numero = cliente.getEndereco().getNumero();
            dto.complemento = cliente.getEndereco().getComplemento();
            dto.bairro = cliente.getEndereco().getBairro();
            dto.cidade = cliente.getEndereco().getCidade();
            dto.uf = cliente.getEndereco().getUf();
            dto.cep = cliente.getEndereco().getCep();
        }
        return dto;
    }

}