package com.example.agropecuariaapi.dto;
import com.example.agropecuariaapi.model.entity.Divida;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DividaDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private Double valor;
    private String vencimento;

    public static DividaDTO create(Divida Divida) {

        DividaDTO dto = null;

        ModelMapper modelMapper = new ModelMapper();
        dto = modelMapper.map(Divida, DividaDTO.class);
        dto.nome = Divida.getCliente().getNome();
        dto.cpf = Divida.getCliente().getCpf();
        dto.email = Divida.getCliente().getEmail();
        dto.telefone= Divida.getCliente().getTelefone();

        return dto;

    }
}
