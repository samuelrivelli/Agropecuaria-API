package com.example.agropecuariaapi.service;

import com.example.agropecuariaapi.exceptions.RegraNegocioException;
import com.example.agropecuariaapi.model.entity.Fornecedor;
import com.example.agropecuariaapi.model.repository.FornecedorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FornecedorServiceTest {

    @Mock
    private FornecedorRepository repository;

    @InjectMocks
    private FornecedorService service;

    private Fornecedor fornecedor;

    @BeforeEach
    void setUp() {
        fornecedor = new Fornecedor();
        fornecedor.setId(1L);
        fornecedor.setRazaoSocial("Fornecedor Teste");
        fornecedor.setCnpj("12345678000199");
        fornecedor.setEmail("fornecedor@email.com");
    }

    @Test
    void testFindAll() {
        List<Fornecedor> fornecedores = Arrays.asList(fornecedor);
        when(repository.findAll()).thenReturn(fornecedores);
        List<Fornecedor> result = service.findAll();
        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(repository.findById(1L)).thenReturn(Optional.of(fornecedor));
        Optional<Fornecedor> result = service.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("Fornecedor Teste", result.get().getRazaoSocial());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        when(repository.save(fornecedor)).thenReturn(fornecedor);
        Fornecedor savedFornecedor = service.save(fornecedor);
        assertNotNull(savedFornecedor);
        assertEquals("Fornecedor Teste", savedFornecedor.getRazaoSocial());
        verify(repository, times(1)).save(fornecedor);
    }

    @Test
    void testExcluir() {
        doNothing().when(repository).delete(fornecedor);
        assertDoesNotThrow(() -> service.excluir(fornecedor));
        verify(repository, times(1)).delete(fornecedor);
    }

    @Test
    void testExcluirComComprasAtreladas() {
        doThrow(DataIntegrityViolationException.class).when(repository).delete(fornecedor);
        RegraNegocioException exception = assertThrows(RegraNegocioException.class, () -> service.excluir(fornecedor));
        assertEquals("O fornecedor não pode ser excluído pois possui compras atreladas a ele", exception.getMessage());
    }

    @Test
    void testValidarFornecedorInvalido() {
        Fornecedor fornecedorInvalido = new Fornecedor();
        assertThrows(RegraNegocioException.class, () -> service.validar(fornecedorInvalido));
    }
}
