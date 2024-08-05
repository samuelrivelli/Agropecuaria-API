package com.example.agropecuariaapi.service;

import com.example.agropecuariaapi.dto.VendaDTO;
import com.example.agropecuariaapi.model.entity.Venda;
import com.example.agropecuariaapi.model.repository.VendaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendaService {

    @Autowired
    private VendaRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Venda> findAll() {
        return repository.findAll();
    }

    public Optional<Venda> findById(Long id) {
        return repository.findById(id);
    }

    public Venda save(Venda venda) {
        return repository.save(venda);
    }

    public Venda update(Venda venda) {
        return repository.save(venda);
    }

    public void excluir(Venda venda) {
        repository.delete(venda);
    }

    public VendaDTO createVenda(VendaDTO vendaDTO) {
        Venda venda = modelMapper.map(vendaDTO, Venda.class);
        Venda savedVenda = repository.save(venda);
        return modelMapper.map(savedVenda, VendaDTO.class);
    }

    public VendaDTO updateVenda(Long id, VendaDTO vendaDTO) throws Exception {
        Optional<Venda> optionalVenda = repository.findById(id);
        if (!optionalVenda.isPresent()) {
            throw new Exception("Venda não encontrada");
        }
        Venda venda = modelMapper.map(vendaDTO, Venda.class);
        venda.setId(id);
        Venda updatedVenda = repository.save(venda);
        return modelMapper.map(updatedVenda, VendaDTO.class);
    }
}
