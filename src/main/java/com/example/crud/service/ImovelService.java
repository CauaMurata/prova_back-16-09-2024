package com.example.crud.service;

import com.example.crud.domain.Comodo;
import com.example.crud.domain.Imovel;
import com.example.crud.dtos.ComodoDto;
import com.example.crud.dtos.ImovelDto;
import com.example.crud.repositorys.ImovelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImovelService {

    @Autowired
    private ImovelRepository imovelRepository;

    public Imovel createImovel(ImovelDto data) {
        Imovel imovel = new Imovel();
        imovel.setDescricao(data.descricao());
        imovel.setDataCompra(data.dataCompra());
        imovel.setEndereco(data.endereco());

        // Adiciona os comodos convertidos de ComodoDto para Comodo
        if (data.comodos() != null) {
            List<Comodo> comodos = data.comodos().stream()
                    .map(dto -> new Comodo(null, dto.getNome()))
                    .toList();
            comodos.forEach(imovel::addComodo);
        }

        this.save(imovel);
        return imovel;
    }

    public void save(Imovel imovel) {
        this.imovelRepository.save(imovel);
    }

    public Imovel updateImovel(Long id, ImovelDto data) {
        return imovelRepository.findById(id)
                .map(existingImovel -> {
                    existingImovel.setDescricao(data.descricao());
                    existingImovel.setDataCompra(data.dataCompra());
                    existingImovel.setEndereco(data.endereco());

                    if (data.comodos() != null) {
                        List<Comodo> comodos = data.comodos().stream()
                                .map(dto -> new Comodo(null, dto.getNome()))
                                .toList();
                        comodos.forEach(existingImovel::addComodo);
                    }

                    return imovelRepository.save(existingImovel);
                }).orElseThrow(() -> new EntityNotFoundException("Imovel not found"));
    }

    public List<Imovel> findAll() {
        return imovelRepository.findAll();
    }

    public Imovel findById(Long id) {
        return imovelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Imovel not found"));
    }

    public void deleteImovel(Long id) {
        Imovel imovel = this.findById(id);
        imovelRepository.delete(imovel);
    }
}
