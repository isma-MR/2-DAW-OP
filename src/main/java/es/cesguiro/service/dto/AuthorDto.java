package es.cesguiro.service.dto;

import jakarta.validation.constraints.*;

public record AuthorDto(
        Long id,

        @NotBlank(message = "El nombre no puede estar vacío")
        String name,

        @NotBlank(message = "La nacionalidad no puede estar vacía")
        String nationality,
        String biographyEs,
        String biographyEn,

        @Min(value = 0, message = "El año de nacimiento debe ser positivo")
        int birthYear,

        @Min(value = 0, message = "El año de defunción debe ser positivo")
        Integer deathYear,

        @NotBlank(message = "El slug no puede estar vacío")
        @Pattern(regexp = "^[a-z0-9-]+$", message = "El slug solo puede contener minúsculas, números y guiones")
        String slug
) {
}
