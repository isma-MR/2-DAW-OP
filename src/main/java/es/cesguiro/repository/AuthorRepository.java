package es.cesguiro.repository;

import es.cesguiro.repository.entity.AuthorEntity;
import es.cesguiro.repository.entity.BookEntity;
import es.cesguiro.service.dto.AuthorDto;

import java.util.Optional;

public interface AuthorRepository {
    AuthorEntity create(AuthorEntity authorEntity);

    Optional<AuthorEntity> findBySlug(String slug);
}
