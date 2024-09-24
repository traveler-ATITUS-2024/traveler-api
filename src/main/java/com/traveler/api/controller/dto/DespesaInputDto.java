package com.traveler.api.controller.dto;

import com.traveler.api.entity.Categoria;
import com.traveler.api.entity.Viagem;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public record DespesaInputDto(
        Categoria categoria,
        Viagem viagem,
        String nome,
        String descricao,
        Date data,
        BigDecimal valor
) {
}
