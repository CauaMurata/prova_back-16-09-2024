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

@Service
public class ImovelService {

    @Autowired
    private ImovelRepository imovelRepository;

    public Imovel createImovel(ImovelDto data) {
        Imovel imovel = new Imovel();
        imovel.setDescricao(data.descricao());
        imovel.setDataCompra(data.dataCompra());
        imovel.setEndereco(data.endereco());

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

    public Imovel removeComodo(Long imovelId, String nomeComodo) {
        return imovelRepository.findById(imovelId)
                .map(imovel -> {
                    Comodo comodoToRemove = imovel.getComodos().stream()
                            .filter(comodo -> comodo.getNome().equals(nomeComodo))
                            .findFirst()
                            .orElseThrow(() -> new EntityNotFoundException("Comodo not found"));

                    imovel.removeComodo(comodoToRemove);

                    return imovelRepository.save(imovel);
                })
                .orElseThrow(() -> new EntityNotFoundException("Imovel not found"));
    }

    public Imovel addComodo(Long imovelId, ComodoDto comodoDto) {
        return imovelRepository.findById(imovelId)
                .map(imovel -> {
                    Comodo newComodo = new Comodo(null, comodoDto.nome());
                    imovel.addComodo(newComodo);
                    return imovelRepository.save(imovel);
                })
                .orElseThrow(() -> new EntityNotFoundException("Imovel not found"));
    }
}
