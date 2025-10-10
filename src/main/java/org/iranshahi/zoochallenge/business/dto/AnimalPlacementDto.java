package org.iranshahi.zoochallenge.business.dto;

import jakarta.validation.constraints.NotNull;

public record AnimalPlacementDto(
        @NotNull
        String roomId) {
}
