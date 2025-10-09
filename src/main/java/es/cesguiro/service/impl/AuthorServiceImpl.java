package es.cesguiro.service.impl;

import es.cesguiro.exception.BusinessException;
import es.cesguiro.mapper.AuthorMapper;
import es.cesguiro.mapper.BookMapper;
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
        List<AuthorEntity> authorEntities = authorRepository.findAll();
        if (authorEntities == null || authorEntities.isEmpty()) {
            throw new BusinessException("There is no books in the system");
        }
        return authorEntities.stream()
                .map(AuthorMapper.getInstance()::fromAuthorEntityToAuthor)
                .map(AuthorMapper.getInstance()::fromAuthorToAuthorDto)
                .toList();
    }

    @Override
    public AuthorDto getBySlug(String slug) {
        return authorRepository
                .findBySlug(slug)
                .map(AuthorMapper.getInstance()::fromAuthorEntityToAuthor)
                .map(AuthorMapper.getInstance()::fromAuthorToAuthorDto)
                .orElseThrow(() -> new BusinessException("Author with slug " + slug + " not found"));
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
    public AuthorDto update(String slug, AuthorDto authorDto) {
        Author author = AuthorMapper.getInstance().fromAuthorDtoToAuthor(authorDto);
        AuthorEntity authorEntity = AuthorMapper.getInstance().fromAuthorToAuthorEntity(author);
        AuthorEntity updatedAuthorEntity = authorRepository.update(slug, authorEntity);
        Author updatedAuthor = AuthorMapper.getInstance().fromAuthorEntityToAuthor(updatedAuthorEntity);
        return AuthorMapper.getInstance().fromAuthorToAuthorDto(updatedAuthor);
    }

    @Override
    public int delete(String slug) {
        return authorRepository.delete(slug);
    }
}
