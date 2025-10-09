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

import java.util.List;
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
    class GetAllTests {
        @Test
        @DisplayName("getAll should throw BusinessException when no authors found")
        void getAll_ShouldThrowBusinessException_WhenNoAuthorsFound() {
            // Arrange
            when(authorRepository.findAll()).thenReturn(null);

            // Act & Assert
            BusinessException exception = assertThrows(BusinessException.class, () -> authorServiceImpl.getAll());
            assertEquals("There is no books in the system", exception.getMessage());
        }

        @Test
        @DisplayName("getAll should return list of AuthorDto when authors are found")
        void getAll_ShouldReturnListOfAuthorDto_WhenAuthorsAreFound() {
            // Arrange
            AuthorEntity authorEntity1 = new AuthorEntity(
                    1L,
                    "author1",
                    "nationality1",
                    "BioEs",
                    "BioEn",
                    1970,
                    null,
                    "slug1"
            );

            AuthorEntity authorEntity2 = new AuthorEntity(
                    2L,
                    "author2",
                    "nationality2",
                    "BioEs2",
                    "BioEn2",
                    1980,
                    null,
                    "slug2"
            );

            when(authorRepository.findAll()).thenReturn(List.of(authorEntity1, authorEntity2));

            // Act
            var authorDtos = authorServiceImpl.getAll();

            // Assert
            assertNotNull(authorDtos, "The returned list should not be null");
            assertEquals(2, authorDtos.size(), "The returned list should contain 2 authors");
        }
    }

    @Nested
    class GetBySlugTests {
        @Test
        @DisplayName("getBySlug should return AuthorDto when author is found")
        void getBySlug_ShouldReturnAuthorDto_WhenAuthorIsFound() {
            // Arrange
            String slug = "slug1";
            AuthorEntity authorEntity = new AuthorEntity(
                    1L,
                    "author1",
                    "nationality1",
                    "BioEs",
                    "BioEn",
                    1970,
                    null,
                    slug
            );

            when(authorRepository.findBySlug(slug)).thenReturn(Optional.of(authorEntity));

            // Act
            AuthorDto authorDto = authorServiceImpl.getBySlug(slug);

            // Assert
            assertNotNull(authorDto, "The returned AuthorDto should not be null");
            assertEquals("author1", authorDto.name(), "The author's name should match");
        }

        @Test
        @DisplayName("getBySlug should throw BusinessException when author is not found")
        void getBySlug_ShouldThrowBusinessException_WhenAuthorIsNotFound() {
            // Arrange
            String slug = "nonexistent-slug";
            when(authorRepository.findBySlug(slug)).thenReturn(Optional.empty());

            // Act & Assert
            BusinessException exception = assertThrows(BusinessException.class, () -> authorServiceImpl.getBySlug(slug));
            assertEquals("Author with slug nonexistent-slug not found", exception.getMessage());
        }
    }

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

    @Nested
    class UpdateAuthorTests {
        @Test
        @DisplayName("update should return updated AuthorDto when author exists")
        void update_ShouldReturnUpdatedAuthorDto_WhenAuthorExists() {
            // Arrange
            String slug = "slug1";
            AuthorDto authorDto = new AuthorDto(
                    1L,
                    "updatedAuthor",
                    "updatedNationality",
                    "UpdatedBioEs",
                    "UpdatedBioEn",
                    1980,
                    null,
                    slug
            );

            AuthorEntity existingAuthorEntity = new AuthorEntity(
                    1L,
                    "author1",
                    "nationality1",
                    "BioEs",
                    "BioEn",
                    1970,
                    null,
                    slug
            );

            AuthorEntity updatedAuthorEntity = new AuthorEntity(
                    1L,
                    "updatedAuthor",
                    "updatedNationality",
                    "UpdatedBioEs",
                    "UpdatedBioEn",
                    1980,
                    null,
                    slug
            );

            when(authorRepository.findBySlug(slug)).thenReturn(Optional.of(existingAuthorEntity));
            when(authorRepository.update(Mockito.eq(slug), Mockito.any(AuthorEntity.class))).thenReturn(updatedAuthorEntity);

            // Act
            AuthorDto updatedAuthorDto = authorServiceImpl.update(slug, authorDto);

            // Assert
            assertNotNull(updatedAuthorDto, "The returned AuthorDto should not be null");
            assertEquals("updatedAuthor", updatedAuthorDto.name(), "The author's name should be updated");
        }

        @Test
        @DisplayName("update should throw BusinessException when author does not exist")
        void update_ShouldThrowBusinessException_WhenAuthorDoesNotExist() {
            // Arrange
            String slug = "nonexistent-slug";
            AuthorDto authorDto = new AuthorDto(
                    1L,
                    "author1",
                    "nationality1",
                    "BioEs",
                    "BioEn",
                    1970,
                    null,
                    slug
            );

            when(authorRepository.findBySlug(slug)).thenReturn(Optional.empty());

            // Act & Assert
            BusinessException exception = assertThrows(BusinessException.class, () -> authorServiceImpl.update(slug, authorDto));
            assertEquals("Author with slug nonexistent-slug not found", exception.getMessage());
        }
    }

    @Nested
    class  DeleteAuthorTests {
        @Test
        @DisplayName("delete should complete without exception when author exists")
        void delete_ShouldCompleteWithoutException_WhenAuthorExists() {
            // Arrange
            String slug = "slug1";
            AuthorEntity existingAuthorEntity = new AuthorEntity(
                    1L,
                    "author1",
                    "nationality1",
                    "BioEs",
                    "BioEn",
                    1970,
                    null,
                    slug
            );

            when(authorRepository.findBySlug(slug)).thenReturn(Optional.of(existingAuthorEntity));
            Mockito.doNothing().when(authorRepository).delete(slug);

            // Act & Assert
            assertDoesNotThrow(() -> authorServiceImpl.delete(slug), "Deleting an existing author should not throw an exception");
        }

        @Test
        @DisplayName("delete should throw BusinessException when author does not exist")
        void delete_ShouldThrowBusinessException_WhenAuthorDoesNotExist() {
            // Arrange
            String slug = "nonexistent-slug";
            when(authorRepository.findBySlug(slug)).thenReturn(Optional.empty());

            // Act & Assert
            BusinessException exception = assertThrows(BusinessException.class, () -> authorServiceImpl.delete(slug));
            assertEquals("Author with slug nonexistent-slug not found", exception.getMessage());
        }
    }
}