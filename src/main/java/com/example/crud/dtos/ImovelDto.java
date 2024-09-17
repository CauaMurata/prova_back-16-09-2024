package com.example.crud.dtos;

import com.example.crud.domain.Comodo;

import java.util.Date;
import java.util.List;

public record ImovelDto(
        String descricao,
        Date dataCompra,
        String endereco,
        List<Comodo> comodos
) {
}
