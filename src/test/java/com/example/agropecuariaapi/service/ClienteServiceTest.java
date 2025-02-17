package com.example.agropecuariaapi.service;

import com.example.agropecuariaapi.exceptions.RegraNegocioException;
import com.example.agropecuariaapi.model.entity.Cliente;
import com.example.agropecuariaapi.model.repository.ClienteRepository;
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
class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService service;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João");
        cliente.setCpf("12345678900");
        cliente.setEmail("joao@example.com");
    }

    @Test
    void testFindAll() {
        List<Cliente> clientes = Arrays.asList(cliente);
        when(repository.findAll()).thenReturn(clientes);
        List<Cliente> result = service.findAll();
        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(repository.findById(1L)).thenReturn(Optional.of(cliente));
        Optional<Cliente> result = service.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("João", result.get().getNome());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        when(repository.save(cliente)).thenReturn(cliente);
        Cliente savedCliente = service.save(cliente);
        assertNotNull(savedCliente);
        assertEquals("João", savedCliente.getNome());
        verify(repository, times(1)).save(cliente);
    }

    @Test
    void testExcluir() {
        doNothing().when(repository).delete(cliente);
        assertDoesNotThrow(() -> service.excluir(cliente));
        verify(repository, times(1)).delete(cliente);
    }


    @Test
    void testValidarClienteInvalido() {
        Cliente clienteInvalido = new Cliente();
        assertThrows(RegraNegocioException.class, () -> service.validar(clienteInvalido));
    }
}
