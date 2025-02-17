package com.example.agropecuariaapi.service;

import com.example.agropecuariaapi.exceptions.RegraNegocioException;
import com.example.agropecuariaapi.model.entity.Produto;
import com.example.agropecuariaapi.model.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    private Produto produto;

    @BeforeEach
    public void setUp() {
        produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Teste");
        produto.setLote("Lote 1");
        produto.setPreco(100.0);
    }

    @Test
    public void testSaveProduto_Valid() {
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);
        Produto savedProduto = produtoService.save(produto);
        assertNotNull(savedProduto);
        assertEquals("Produto Teste", savedProduto.getNome());
        verify(produtoRepository, times(1)).save(produto);
    }

    @Test
    public void testSaveProduto_InvalidNome() {
        produto.setNome(null);
        assertThrows(RegraNegocioException.class, () -> produtoService.save(produto));
    }

    @Test
    public void testExcluirProduto() {
        doNothing().when(produtoRepository).delete(any(Produto.class));
        produtoService.excluir(produto);
        verify(produtoRepository, times(1)).delete(produto);
    }

    @Test
    public void testValidarProduto() {
        assertDoesNotThrow(() -> produtoService.validar(produto));
    }
}
