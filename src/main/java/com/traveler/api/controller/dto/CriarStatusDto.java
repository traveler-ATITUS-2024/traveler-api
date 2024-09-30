package com.traveler.api.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record CriarStatusDto(@NotBlank String status) {

}
