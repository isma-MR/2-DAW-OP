package es.cesguiro.repository;

import es.cesguiro.repository.entity.AuthorEntity;
import es.cesguiro.repository.entity.BookEntity;
import es.cesguiro.service.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    List<AuthorEntity> findAll();

    AuthorEntity create(AuthorEntity authorEntity);

    Optional<AuthorEntity> findBySlug(String slug);

    AuthorEntity update(String slug, AuthorEntity authorEntity);

    int delete(String slug);
}
