package es.cesguiro.repository.entity;

public record AuthorEntity(
        String name,
        String nationality,
        String biographyEs,
        String biographyEn,
        int birthYear,
        Integer deathYear,
        String slug
) {

}
