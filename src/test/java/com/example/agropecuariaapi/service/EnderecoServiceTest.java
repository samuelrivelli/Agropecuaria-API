package com.example.agropecuariaapi.service;

import com.example.agropecuariaapi.model.entity.Endereco;
import com.example.agropecuariaapi.model.repository.EnderecoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnderecoServiceTest {

    @Mock
    private EnderecoRepository repository;

    @InjectMocks
    private EnderecoService service;

    private Endereco endereco;

    @BeforeEach
    void setUp() {
        endereco = new Endereco();
        endereco.setId(1L);
        endereco.setBairro("Bairro Teste");
        endereco.setNumero(123);
        endereco.setCidade("Cidade Teste");
    }

    @Test
    void testFindAll() {
        List<Endereco> enderecos = Arrays.asList(endereco);
        when(repository.findAll()).thenReturn(enderecos);
        List<Endereco> result = service.findAll();
        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(repository.findById(1L)).thenReturn(Optional.of(endereco));
        Optional<Endereco> result = service.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("Bairro Teste", result.get().getBairro());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        when(repository.save(endereco)).thenReturn(endereco);
        Endereco savedEndereco = service.save(endereco);
        assertNotNull(savedEndereco);
        assertEquals("Bairro Teste", savedEndereco.getBairro());
        verify(repository, times(1)).save(endereco);
    }

    @Test
    void testExcluir() {
        doNothing().when(repository).delete(endereco);
        assertDoesNotThrow(() -> service.excluir(endereco));
        verify(repository, times(1)).delete(endereco);
    }
}
