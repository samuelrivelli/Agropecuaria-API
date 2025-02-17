package com.example.agropecuariaapi.service;

import com.example.agropecuariaapi.exceptions.RegraNegocioException;
import com.example.agropecuariaapi.model.entity.Cliente;
import com.example.agropecuariaapi.model.entity.Divida;
import com.example.agropecuariaapi.model.repository.DividaRepository;
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
class DividasServiceTest {

    @Mock
    private DividaRepository repository;

    @InjectMocks
    private DividasService service;

    private Divida divida;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setNome("Cliente Teste");

        divida = new Divida();
        divida.setId(1L);
        divida.setCliente(cliente);
        divida.setValor(500.00);
    }

    @Test
    void testFindAll() {
        List<Divida> dividas = Arrays.asList(divida);
        when(repository.findAll()).thenReturn(dividas);
        List<Divida> result = service.findAll();
        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(repository.findById(1L)).thenReturn(Optional.of(divida));
        Optional<Divida> result = service.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("Cliente Teste", result.get().getCliente().getNome());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        when(repository.save(any(Divida.class))).thenReturn(divida);
        Divida savedDivida = service.save(divida);
        assertNotNull(savedDivida);
        assertEquals("Cliente Teste", savedDivida.getCliente().getNome());
        verify(repository, times(1)).save(any(Divida.class));
    }

    @Test
    void testExcluir() {
        doNothing().when(repository).delete(divida);
        assertDoesNotThrow(() -> service.excluir(divida));
        verify(repository, times(1)).delete(divida);
    }

    @Test
    void testValidarDividaInvalida() {
        Divida dividaInvalida = new Divida();
        assertThrows(RegraNegocioException.class, () -> service.validar(dividaInvalida));
    }
}
