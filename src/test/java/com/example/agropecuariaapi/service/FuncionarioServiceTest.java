package com.example.agropecuariaapi.service;

import com.example.agropecuariaapi.exceptions.RegraNegocioException;
import com.example.agropecuariaapi.model.entity.Funcionario;
import com.example.agropecuariaapi.model.repository.FuncionarioRepository;
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
class FuncionarioServiceTest {

    @Mock
    private FuncionarioRepository repository;

    @InjectMocks
    private FuncionarioService service;

    private Funcionario funcionario;

    @BeforeEach
    void setUp() {
        funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("João Silva");
        funcionario.setCpf("123.456.789-00");
        funcionario.setEmail("joao@email.com");
    }

    @Test
    void testFindAll() {
        List<Funcionario> funcionarios = Arrays.asList(funcionario);
        when(repository.findAll()).thenReturn(funcionarios);
        List<Funcionario> result = service.findAll();
        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(repository.findById(1L)).thenReturn(Optional.of(funcionario));
        Optional<Funcionario> result = service.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("João Silva", result.get().getNome());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        when(repository.save(funcionario)).thenReturn(funcionario);
        Funcionario savedFuncionario = service.save(funcionario);
        assertNotNull(savedFuncionario);
        assertEquals("João Silva", savedFuncionario.getNome());
        verify(repository, times(1)).save(funcionario);
    }

    @Test
    void testExcluir() {
        doNothing().when(repository).delete(funcionario);
        assertDoesNotThrow(() -> service.excluir(funcionario));
        verify(repository, times(1)).delete(funcionario);
    }

    @Test
    void testValidarFuncionarioInvalido() {
        Funcionario funcionarioInvalido = new Funcionario();
        assertThrows(RegraNegocioException.class, () -> service.validar(funcionarioInvalido));
    }
}
