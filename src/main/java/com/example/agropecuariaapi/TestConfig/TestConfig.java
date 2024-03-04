package com.example.agropecuariaapi.TestConfig;

import com.example.agropecuariaapi.model.entity.Cliente;
import com.example.agropecuariaapi.model.entity.Endereco;
import com.example.agropecuariaapi.model.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;

@Configuration
public class TestConfig implements CommandLineRunner {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void run(String... args) throws Exception {
        Cliente c = new Cliente();
        c.setNome("Jose Ferreira da Silva");
        c.setCpf("1620555");
        c.setEmail("email@email");
        c.setTelefone("8484554");

        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua Exemplo");
        endereco.setNumero(123);
        endereco.setComplemento("APTO 101");
        endereco.setBairro("Centro");
        endereco.setCidade("Cidade Exemplo");
        endereco.setUf("UF");
        endereco.setCep("12345-678");

        c.setEndereco(endereco);

        clienteRepository.save(c);

    }
}
