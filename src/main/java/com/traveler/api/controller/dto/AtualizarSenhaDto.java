package com.traveler.api.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record AtualizarSenhaDto(String senhaAtual, String senha) {
}
