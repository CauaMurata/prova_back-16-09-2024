package com.example.crud.dtos;

import com.example.crud.domain.Comodo;

import java.time.LocalDate;
import java.util.List;

public record ImovelDto(
        String descricao,
        LocalDate dataCompra,
        String endereco,
        List<Comodo> comodos
) {}
