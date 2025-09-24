package es.cesguiro.service.impl;

import es.cesguiro.mapper.AuthorMapper;
import es.cesguiro.model.Author;
import es.cesguiro.repository.AuthorRepository;
import es.cesguiro.repository.entity.AuthorEntity;
import es.cesguiro.service.dto.AuthorDto;
import es.cesguiro.service.AuthorService;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<AuthorDto> getAll() {
        return List.of();
    }

    @Override
    public AuthorDto getBySlug(String slug) {
        return null;
    }

    @Override
    public AuthorDto create(AuthorDto authorDto) {
        Author author = AuthorMapper.getInstance().fromAuthorDtoToAuthor(authorDto);
        AuthorEntity authorEntity = AuthorMapper.getInstance().fromAuthorToAuthorEntity(author);
        AuthorEntity newAuthorEntity = authorRepository.create(authorEntity);
        Author newAuthor = AuthorMapper.getInstance().fromAuthorEntityToAuthor(newAuthorEntity);
        return AuthorMapper.getInstance().fromAuthorToAuthorDto(newAuthor);
    }

    @Override
    public AuthorDto update(AuthorDto authorDto) {
        return null;
    }

    @Override
    public int delete(String slug) {
        return 0;
    }
}
