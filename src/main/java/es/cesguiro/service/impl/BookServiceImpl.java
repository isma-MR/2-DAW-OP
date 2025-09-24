package es.cesguiro.service.impl;

import es.cesguiro.mapper.BookMapper;
import es.cesguiro.repository.entity.BookEntity;
import es.cesguiro.service.dto.BookDto;
import es.cesguiro.exception.BusinessException;
import es.cesguiro.repository.BookRepository;
import es.cesguiro.service.BookService;

import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookDto> getAll(int page, int size) {
        List<BookDto> bookDtos = findAll(page, size);
        if (bookDtos.isEmpty()) {
            throw new BusinessException("No books found");
        }
        return bookDtos;
    }

    @Override
    public List<BookDto> findAll(int page, int size) {
        List<BookEntity> entities = bookRepository.findAll(page, size); // solo una llamada

        if (entities == null) {
            return List.of();
        }

        return entities.stream()
                .map(BookMapper.getInstance()::fromBookEntityToBook)
                .map(BookMapper.getInstance()::fromBookToBookDto)
                .toList();
    }


    @Override
    public BookDto getByIsbn(String isbn) {
        return findByIsbn(isbn)
                .orElseThrow(() -> new BusinessException("Book with isbn " + isbn + " not found"));
    }

    @Override
    public Optional<BookDto> findByIsbn(String isbn) {
        return bookRepository
                .findByIsbn(isbn)
                .map(BookMapper.getInstance()::fromBookEntityToBook)
                .map(BookMapper.getInstance()::fromBookToBookDto);
    }

    @Override
    public BookDto create(BookDto bookDto) {
        return null;
    }

    @Override
    public BookDto update(BookDto bookDto) {
        return null;
    }

    @Override
    public void delete(String isbn) {

    }
}
