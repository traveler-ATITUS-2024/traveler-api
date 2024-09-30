package com.traveler.api.controller.dto;

import com.traveler.api.entity.Categoria;
import com.traveler.api.entity.Despesa;
import com.traveler.api.entity.Viagem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public record DespesaInputDto(
        @NotNull Long categoriaId,
        @NotNull Long viagemId,
        @NotBlank String nome,
        @NotBlank String descricao,
        @NotNull Date data,
        @NotNull BigDecimal valor
) {

    public Despesa toDespesaWithId(Long id) {
        return new Despesa(
                id,
                categoriaId,
                viagemId,
                nome,
                descricao,
                new Timestamp(data.getTime()),
                valor
        );
    }
}
