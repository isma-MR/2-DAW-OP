package es.cesguiro.service.impl;

import es.cesguiro.exception.BusinessException;
import es.cesguiro.mapper.AuthorMapper;
import es.cesguiro.model.Author;
import es.cesguiro.repository.AuthorRepository;
import es.cesguiro.repository.entity.AuthorEntity;
import es.cesguiro.service.dto.AuthorDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorServiceImpl;

    @Nested
    @DisplayName("Tests for create method")
    class CreateAuthorTests {
        @Test
        @DisplayName("create should return created AuthorDto")
        void create_ShouldReturnCreatedAuthorDto() {
            // Arrange
            AuthorDto authorDto = new AuthorDto(
                    1L,
                    "author1",
                    "nationality1",
                    "BioEs",
                    "BioEn",
                    1970,
                    null,
                    "slug1"
            );

            // Mock repository behavior
            when(authorRepository.create(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

            // Act
            AuthorDto createdAuthorDto = authorServiceImpl.create(authorDto);
            // Assert
            assertAll(
                    () -> assertNotNull(createdAuthorDto, "Created AuthorDto should not be null"),
                    () -> assertEquals(authorDto.name(), createdAuthorDto.name(), "Names should match"),
                    () -> assertEquals(authorDto.nationality(), createdAuthorDto.nationality())
            );
        }

        @Test
        @DisplayName("create author with null AuthorDto should throw exception")
        void create_WithNullAuthorDto_ShouldThrowException() {
            AuthorDto authorDto = new AuthorDto(
                    1L,
                    "author1",
                    "nationality1",
                    "BioEs",
                    "BioEn",
                    1970,
                    null,
                    "slug1"
            );

            // Act
            AuthorDto createdAuthorDto = null;

            assertThrows(BusinessException.class, () -> authorServiceImpl.create(createdAuthorDto));
        }

        @Test
        @DisplayName("create author with existing slug should throw exception")
        void create_WithExistingSlug_ShouldThrowException() {
            Author existingAuthor = new Author(
                    1L,
                    "author1",
                    "nationality1",
                    "BioEs",
                    "BioEn",
                    1970,
                    null,
                    "slug1"
            );

            AuthorDto existingAuthorDto = AuthorMapper.getInstance().fromAuthorToAuthorDto(existingAuthor);

            assertThrows(BusinessException.class, () -> authorServiceImpl.create(existingAuthorDto));
        }
    }
}