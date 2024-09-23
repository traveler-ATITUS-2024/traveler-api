package com.traveler.api.controller.dto;

import com.traveler.api.entity.Status;
import com.traveler.api.entity.Usuario;
import com.traveler.api.entity.Viagem;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public record ViagemInputDto(
        Status status,
        String nome,
        Date dataIda,
        Date dataVolta,
        BigDecimal valorPrv,
        BigDecimal valorReal,
        String latitude,
        String longitude) {


    public Viagem toViagem() {
        return new Viagem(nome,
                status,
                new Timestamp(dataIda.getTime()),
                new Timestamp(dataVolta.getTime()),
                valorPrv,
                valorReal,
                latitude,
                longitude);
    }

    public Viagem toViagemWithId(Long id, Usuario usuario) {
        return new Viagem(
                id,
                usuario,
                nome,
                status,
                new Timestamp(dataIda.getTime()),
                new Timestamp(dataVolta.getTime()),
                valorPrv,
                valorReal,
                latitude,
                longitude);
    }
}
