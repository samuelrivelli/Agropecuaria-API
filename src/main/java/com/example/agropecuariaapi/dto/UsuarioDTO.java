package com.example.agropecuariaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import com.example.agropecuariaapi.model.entity.Usuario;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Long id;
    private String login;
    private String senha;
    private String senhaRepeticao;
    private boolean admin;

    public static UsuarioDTO create(Usuario usuario) {
        ModelMapper modelMapper = new ModelMapper();
        UsuarioDTO dto = modelMapper.map(usuario, UsuarioDTO.class);
        return dto;
    }
}