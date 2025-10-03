package es.cesguiro.service;

import es.cesguiro.service.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<BookDto> getAll(int page, int size);

    BookDto getByIsbn(String isbn);

    BookDto create(BookDto bookDto);

    BookDto update(BookDto bookDto);

    void delete(String isbn);

}
