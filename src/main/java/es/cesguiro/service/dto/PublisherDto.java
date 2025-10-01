package es.cesguiro.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PublisherDto(
        Long id,

        @NotBlank(message = "El nombre no puede estar vacío")
        String name,

        @NotBlank(message = "El slug no puede estar vacío")
        @Pattern(regexp = "^[a-z0-9-]+$", message = "El slug solo puede contener minúsculas, números y guiones")
        String slug
) {
}
