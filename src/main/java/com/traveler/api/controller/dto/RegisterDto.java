package com.traveler.api.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterDto(
        String nome,
        String senha,
        String email
) {
}