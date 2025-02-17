package com.example.agropecuariaapi.service;

import com.example.agropecuariaapi.exceptions.RegraNegocioException;
import com.example.agropecuariaapi.model.entity.CompraFornecedor;
import com.example.agropecuariaapi.model.entity.Fornecedor;
import com.example.agropecuariaapi.model.repository.CompraFornecedorRepository;
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
class CompraFornecedorServiceTest {

    @Mock
    private CompraFornecedorRepository repository;

    @InjectMocks
    private CompraFornecedorService service;

    private CompraFornecedor compraFornecedor;
    private Fornecedor fornecedor;

    @BeforeEach
    void setUp() {
        compraFornecedor = new CompraFornecedor();
        fornecedor = new Fornecedor();
        fornecedor.setId(1L);
        fornecedor.setRazaoSocial("Fornecedor teste");
        compraFornecedor.setId(1L);
        compraFornecedor.setFornecedor(fornecedor);
        compraFornecedor.setValor(100.00);
    }

    @Test
    void testFindAll() {
        List<CompraFornecedor> compras = Arrays.asList(compraFornecedor);
        when(repository.findAll()).thenReturn(compras);
        List<CompraFornecedor> result = service.findAll();
        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(repository.findById(1L)).thenReturn(Optional.of(compraFornecedor));
        Optional<CompraFornecedor> result = service.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("Fornecedor teste", result.get().getFornecedor().getRazaoSocial());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        when(repository.save(any(CompraFornecedor.class))).thenReturn(compraFornecedor);
        CompraFornecedor savedCompra = service.save(compraFornecedor);
        assertNotNull(savedCompra);
        assertEquals("Fornecedor teste", savedCompra.getFornecedor().getRazaoSocial());
        verify(repository, times(1)).save(any(CompraFornecedor.class));
    }

    @Test
    void testExcluir() {
        doNothing().when(repository).delete(compraFornecedor);
        assertDoesNotThrow(() -> service.excluir(compraFornecedor));
        verify(repository, times(1)).delete(compraFornecedor);
    }

    @Test
    void testValidarCompraFornecedorInvalido() {
        CompraFornecedor compraInvalida = new CompraFornecedor();
        assertThrows(RegraNegocioException.class, () -> service.validar(compraInvalida));
    }
}
