package com.example.agropecuariaapi.service;

import com.example.agropecuariaapi.dto.VendaDTO;
import com.example.agropecuariaapi.exceptions.RegraNegocioException;
import com.example.agropecuariaapi.model.entity.Cliente;
import com.example.agropecuariaapi.model.entity.Venda;
import com.example.agropecuariaapi.model.repository.VendaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VendaServiceTest {

    @InjectMocks
    private VendaService vendaService;

    @Mock
    private VendaRepository vendaRepository;

    @Mock
    private ModelMapper modelMapper;

    private Venda venda;
    private VendaDTO vendaDTO;
    private Cliente cliente;
    @BeforeEach
    public void setUp() {
        cliente = new Cliente();
        cliente.setNome("Cliente Teste");

        venda = new Venda();
        venda.setId(1L);
        venda.setCliente(cliente);
        venda.setFormaDePagamento("Cartão");

        vendaDTO = new VendaDTO();
        vendaDTO.setNomeCliente(cliente.getNome());
        vendaDTO.setFormaDePagamento("Cartão");
    }

    @Test
    public void testSaveVenda_Valid() {
        when(vendaRepository.save(any(Venda.class))).thenReturn(venda);
        Venda savedVenda = vendaService.save(venda);
        assertNotNull(savedVenda);
        assertEquals("Cliente Teste", savedVenda.getCliente().getNome());
        verify(vendaRepository, times(1)).save(venda);
    }

    @Test
    public void testSaveVenda_InvalidCliente() {
        venda.setCliente(null);
        assertThrows(RegraNegocioException.class, () -> vendaService.save(venda));
    }

    @Test
    public void testCreateVenda() {
        when(modelMapper.map(any(VendaDTO.class), eq(Venda.class))).thenReturn(venda);
        when(vendaRepository.save(any(Venda.class))).thenReturn(venda);
        when(modelMapper.map(any(Venda.class), eq(VendaDTO.class))).thenReturn(vendaDTO);

        VendaDTO createdVendaDTO = vendaService.createVenda(vendaDTO);
        assertNotNull(createdVendaDTO);
        assertEquals("Cliente Teste", createdVendaDTO.getNomeCliente());
    }

    @Test
    public void testUpdateVenda_Valid() {
        when(vendaRepository.findById(1L)).thenReturn(Optional.of(venda));
        when(modelMapper.map(any(VendaDTO.class), eq(Venda.class))).thenReturn(venda);
        when(vendaRepository.save(any(Venda.class))).thenReturn(venda);
        when(modelMapper.map(any(Venda.class), eq(VendaDTO.class))).thenReturn(vendaDTO);

        VendaDTO updatedVendaDTO = vendaService.updateVenda(1L, vendaDTO);
        assertNotNull(updatedVendaDTO);
        assertEquals("Cliente Teste", updatedVendaDTO.getNomeCliente());
    }

    @Test
    public void testUpdateVenda_VendaNotFound() {
        when(vendaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RegraNegocioException.class, () -> vendaService.updateVenda(1L, vendaDTO));
    }

    @Test
    public void testExcluirVenda() {
        doNothing().when(vendaRepository).delete(any(Venda.class));
        vendaService.excluir(venda);
        verify(vendaRepository, times(1)).delete(venda);
    }

    @Test
    public void testValidarVenda_InvalidCliente() {
        venda.setCliente(null);
        assertThrows(RegraNegocioException.class, () -> vendaService.validar(venda));
    }

    @Test
    public void testValidarVenda_Valid() {
        assertDoesNotThrow(() -> vendaService.validar(venda));
    }
}
