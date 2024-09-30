package com.traveler.api.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record CriarCategoriaDto(@NotBlank String nome) {
}