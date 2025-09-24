package es.cesguiro.mapper;

import es.cesguiro.exception.BusinessException;
import es.cesguiro.model.Author;
import es.cesguiro.repository.entity.AuthorEntity;
import es.cesguiro.service.dto.AuthorDto;

public class AuthorMapper {

    private  static AuthorMapper instance;

    private AuthorMapper() {}

    public static AuthorMapper getInstance() {
        if (instance == null) {
            instance = new AuthorMapper();
        }
        return instance;
    }

    public Author fromAuthorEntityToAuthor(es.cesguiro.repository.entity.AuthorEntity authorEntity) {
        return new Author(
                authorEntity.name(),
                authorEntity.nationality(),
                authorEntity.biographyEs(),
                authorEntity.biographyEn(),
                authorEntity.birthYear(),
                authorEntity.deathYear(),
                authorEntity.slug()
        );
    }

    public AuthorEntity fromAuthorToAuthorEntity(Author author) {
        return new AuthorEntity(
                author.getName(),
                author.getNationality(),
                author.getBiographyEs(),
                author.getBiographyEn(),
                author.getBirthYear(),
                author.getDeathYear(),
                author.getSlug()
        );
    }

    public AuthorDto fromAuthorToAuthorDto(Author author) {
        return new AuthorDto(
                author.getName(),
                author.getNationality(),
                author.getBiographyEs(),
                author.getBiographyEn(),
                author.getBirthYear(),
                author.getDeathYear(),
                author.getSlug()
        );
    }

    public Author fromAuthorDtoToAuthor(AuthorDto authorDto) {
        return new Author(
                authorDto.name(),
                authorDto.nationality(),
                authorDto.biographyEs(),
                authorDto.biographyEn(),
                authorDto.birthYear(),
                authorDto.deathYear(),
                authorDto.slug()
        );
    }

}
