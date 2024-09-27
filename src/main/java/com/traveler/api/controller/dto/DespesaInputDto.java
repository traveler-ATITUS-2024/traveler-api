package com.traveler.api.controller.dto;

import com.traveler.api.entity.Categoria;
import com.traveler.api.entity.Viagem;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public record DespesaInputDto(
        Long categoriaId,
        Long viagemId,
        String nome,
        String descricao,
        Date data,
        BigDecimal valor
) {
}
