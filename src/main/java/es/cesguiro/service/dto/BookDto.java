package es.cesguiro.service.dto;

import jakarta.validation.ValidationException;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static es.cesguiro.validation.DtoValidator.validate;

public record BookDto(
        Long id,
        @NotNull
        @Pattern(regexp = "^\\d{13}$", message = "Debe contener exactamente 13 dígitos")
        String isbn,
        String titleEs,
        String titleEn,
        String synopsisEs,
        String synopsisEn,
        @Min(value = 0, message = "El precio base no puede ser negativo")
        BigDecimal basePrice,
        @Min(value = 0, message = "El porcentaje de descuento no puede ser menor que 0")
        @Max(value = 100, message = "El porcentaje de descuento no puede ser mayor que 100")
        double discountPercentage,
        BigDecimal price,
        String cover,
        LocalDate publicationDate,
        @NotNull
        PublisherDto publisher,
        List<AuthorDto> authors
) {
    public BookDto {
        if (titleEs == null && titleEn == null) {
            throw new ValidationException("Al menos uno de los títulos (español o inglés) debe estar presente.");
        }

        if (authors == null) {
            authors = List.of();
        } else {
            authors = List.copyOf(authors);
        }
    }
}
