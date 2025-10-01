package es.cesguiro.repository.entity;

public record AuthorEntity(
        Long id,
        String name,
        String nationality,
        String biographyEs,
        String biographyEn,
        int birthYear,
        Integer deathYear,
        String slug
) {

}
