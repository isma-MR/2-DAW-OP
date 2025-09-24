package es.cesguiro.repository;

import es.cesguiro.repository.entity.AuthorEntity;
import es.cesguiro.service.dto.AuthorDto;

public interface AuthorRepository {
    AuthorEntity create(AuthorEntity authorEntity);
}
