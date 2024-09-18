package com.traveler.api.controller.dto;

import com.traveler.api.entity.Status;

import java.math.BigDecimal;
import java.sql.Date;

public record CriarViagemDto(Status status, String nome, Date dataIda, Date dataVolta, BigDecimal valorPrv, BigDecimal valorReal, String latitude, String longitude) {
}
