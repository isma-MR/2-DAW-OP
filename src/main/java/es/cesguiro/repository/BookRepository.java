package es.cesguiro.repository;

import es.cesguiro.repository.entity.BookEntity;
import es.cesguiro.service.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<BookEntity> findAll(int page, int size);

    Optional<BookEntity> findByIsbn(String isbn);
}
