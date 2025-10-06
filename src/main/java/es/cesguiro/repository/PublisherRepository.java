package es.cesguiro.repository;

import es.cesguiro.repository.entity.AuthorEntity;
import es.cesguiro.repository.entity.PublisherEntity;

import java.util.Optional;

public interface PublisherRepository {
    PublisherEntity create(PublisherEntity publisherEntity);

    Optional<PublisherEntity> findBySlug(String slug);
}
