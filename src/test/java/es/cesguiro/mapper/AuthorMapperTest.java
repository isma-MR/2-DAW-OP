package es.cesguiro.mapper;

import es.cesguiro.model.Author;
import es.cesguiro.repository.entity.AuthorEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorMapperTest {

    @Nested
    @DisplayName("Testing AuthorEntity to Author mapping")
    class FromAuthorEntityToAuthor {
        @Test
        @DisplayName("Test map AuthorEntity to Author")
        void toAuthor() {
            // Arrange
            AuthorEntity authorEntity = new AuthorEntity(1L, "name", "nationality", "biographyEs", "biographyEn", 1990, 2020, "slug");

            // Act
            var author = AuthorMapper.getInstance().fromAuthorEntityToAuthor(authorEntity);

            // Assert
            assertAll(
                    () -> assertEquals(authorEntity.id(), author.getId(), "IDs should match"),
                    () -> assertEquals(authorEntity.name(), author.getName(), "Names should match"),
                    () -> assertEquals(authorEntity.nationality(), author.getNationality(), "Nationalities should match"),
                    () -> assertEquals(authorEntity.biographyEs(), author.getBiographyEs(), "BiographyEs should match"),
                    () -> assertEquals(authorEntity.biographyEn(), author.getBiographyEn(), "BiographyEn should match"),
                    () -> assertEquals(authorEntity.birthYear(), author.getBirthYear(), "Birth years should match"),
                    () -> assertEquals(authorEntity.deathYear(), author.getDeathYear(), "Death years should match"),
                    () -> assertEquals(authorEntity.slug(), author.getSlug(), "Slugs should match")
            );
        }

        @Test
        @DisplayName("Test map null AuthorEntity to Author throws BusinessException")
        void toAuthor_NullAuthorEntity_ThrowsBusinessException() {
            // Arrange
            AuthorEntity authorEntity = null;
            // Act & Assert
            var exception = assertThrows(es.cesguiro.exception.BusinessException.class, () -> AuthorMapper.getInstance().fromAuthorEntityToAuthor(authorEntity));
            assertEquals("AuthorEntity cannot be null", exception.getMessage());
        }

        @Test
        @DisplayName("Test map List<AuthorEntity> to Author")
        void toAuthorList() {
            // Arrange
            var authorEntities = java.util.List.of(
                    new AuthorEntity(1L, "name1", "nationality1", "biographyEs1", "biographyEn1", 1990, 2020, "slug1"),
                    new AuthorEntity(2L, "name2", "nationality2", "biographyEs2", "biographyEn2", 1980, null, "slug2")
            );

            // Act
            var authors = authorEntities.stream().map(AuthorMapper.getInstance()::fromAuthorEntityToAuthor).toList();

            // Assert
            assertAll(
                    () -> assertEquals(2, authors.size(), "There should be two authors"),
                    () -> {
                        var author1 = authors.get(0);
                        var entity1 = authorEntities.get(0);
                        assertAll(
                                () -> assertEquals(entity1.id(), author1.getId(), "IDs should match"),
                                () -> assertEquals(entity1.name(), author1.getName(), "Names should match"),
                                () -> assertEquals(entity1.nationality(), author1.getNationality(), "Nationalities should match"),
                                () -> assertEquals(entity1.biographyEs(), author1.getBiographyEs(), "BiographyEs should match"),
                                () -> assertEquals(entity1.biographyEn(), author1.getBiographyEn(), "BiographyEn should match"),
                                () -> assertEquals(entity1.birthYear(), author1.getBirthYear(), "Birth years should match"),
                                () -> assertEquals(entity1.deathYear(), author1.getDeathYear(), "Death years should match"),
                                () -> assertEquals(entity1.slug(), author1.getSlug(), "Slugs should match")
                        );
                    },
                    () -> {
                        var author2 = authors.get(1);
                        var entity2 = authorEntities.get(1);
                        assertAll(
                                () -> assertEquals(entity2.id(), author2.getId(), "IDs should match"),
                                () -> assertEquals(entity2.name(), author2.getName(), "Names should match"),
                                () -> assertEquals(entity2.nationality(), author2.getNationality(), "Nationalities should match"),
                                () -> assertEquals(entity2.biographyEs(), author2.getBiographyEs(), "BiographyEs should match"),
                                () -> assertEquals(entity2.biographyEn(), author2.getBiographyEn(), "BiographyEn should match"),
                                () -> assertEquals(entity2.birthYear(), author2.getBirthYear(), "Birth years should match"),
                                () -> assertEquals(entity2.deathYear(), author2.getDeathYear(), "Death years should match"),
                                () -> assertEquals(entity2.slug(), author2.getSlug(), "Slugs should match")
                        );
                    }
            );
        }
    }

    @Nested
    @DisplayName("Testing Author to AuthorEntity mapping")
    class FromAuthorToAuthorEntity {
        @Test
        @DisplayName("Test map Author to AuthorEntity")
        void toAuthorEntity() {
            var author = new Author(1L, "name", "nationality", "biographyEs", "biographyEn", 1990, 2020, "slug");

            var authorEntity = AuthorMapper.getInstance().fromAuthorToAuthorEntity(author);

            assertAll(
                    () -> assertEquals(author.getId(), authorEntity.id(), "IDs should match"),
                    () -> assertEquals(author.getName(), authorEntity.name(), "Names should match"),
                    () -> assertEquals(author.getNationality(), authorEntity.nationality(), "Nationalities should match"),
                    () -> assertEquals(author.getBiographyEs(), authorEntity.biographyEs(), "BiographyEs should match"),
                    () -> assertEquals(author.getBiographyEn(), authorEntity.biographyEn(), "BiographyEn should match"),
                    () -> assertEquals(author.getBirthYear(), authorEntity.birthYear(), "Birth years should match"),
                    () -> assertEquals(author.getDeathYear(), authorEntity.deathYear(), "Death years should match"),
                    () -> assertEquals(author.getSlug(), authorEntity.slug(), "Slugs should match")
            );
        }

        @Test
        @DisplayName("Test map null Author to AuthorEntity throws BusinessException")
        void toAuthorEntity_NullAuthor_ThrowsBusinessException() {
            Author author = null;

            var exception = assertThrows(es.cesguiro.exception.BusinessException.class, () -> AuthorMapper.getInstance().fromAuthorToAuthorEntity(author));

            assertEquals("Author cannot be null", exception.getMessage());
        }

        @Test
        @DisplayName("Test map List<Author> to AuthorEntity")
        void toAuthorEntityList() {
            var authors = java.util.List.of(
                    new Author(1L, "name1", "nationality1", "biographyEs1", "biographyEn1", 1990, 2020, "slug1"),
                    new Author(2L, "name2", "nationality2", "biographyEs2", "biographyEn2", 1980, null, "slug2")
            );

            var authorEntities = authors.stream().map(AuthorMapper.getInstance()::fromAuthorToAuthorEntity).toList();

            assertAll(
                    () -> assertEquals(2, authorEntities.size(), "There should be two author entities"),
                    () -> {
                        var author1 = authors.get(0);
                        var entity1 = authorEntities.get(0);
                        assertAll(
                                () -> assertEquals(author1.getId(), entity1.id(), "IDs should match"),
                                () -> assertEquals(author1.getName(), entity1.name(), "Names should match"),
                                () -> assertEquals(author1.getNationality(), entity1.nationality(), "Nationalities should match"),
                                () -> assertEquals(author1.getBiographyEs(), entity1.biographyEs(), "BiographyEs should match"),
                                () -> assertEquals(author1.getBiographyEn(), entity1.biographyEn(), "BiographyEn should match"),
                                () -> assertEquals(author1.getBirthYear(), entity1.birthYear(), "Birth years should match"),
                                () -> assertEquals(author1.getDeathYear(), entity1.deathYear(), "Death years should match"),
                                () -> assertEquals(author1.getSlug(), entity1.slug(), "Slugs should match")
                        );
                    },
                    () -> {
                        var author2 = authors.get(1);
                        var entity2 = authorEntities.get(1);
                        assertAll(
                                () -> assertEquals(author2.getId(), entity2.id(), "IDs should match"),
                                () -> assertEquals(author2.getName(), entity2.name(), "Names should match"),
                                () -> assertEquals(author2.getNationality(), entity2.nationality(), "Nationalities should match"),
                                () -> assertEquals(author2.getBiographyEs(), entity2.biographyEs(), "BiographyEs should match"),
                                () -> assertEquals(author2.getBiographyEn(), entity2.biographyEn(), "BiographyEn should match"),
                                () -> assertEquals(author2.getBirthYear(), entity2.birthYear(), "Birth years should match"),
                                () -> assertEquals(author2.getDeathYear(), entity2.deathYear(), "Death years should match"),
                                () -> assertEquals(author2.getSlug(), entity2.slug(), "Slugs should match")
                        );
                    }
            );
        }
    }

    @Nested
    @DisplayName("Testing Author to AuthorDto mapping")
    class FromAuthorToAuthorDto {
        @Test
        @DisplayName("Test map Author to AuthorDto")
        void toAuthorDto() {
            var author = new Author(1L, "name", "nationality", "biographyEs", "biographyEn", 1990, 2020, "slug");

            var authorDto = AuthorMapper.getInstance().fromAuthorToAuthorDto(author);

            assertAll(
                    () -> assertEquals(author.getId(), authorDto.id(), "IDs should match"),
                    () -> assertEquals(author.getName(), authorDto.name(), "Names should match"),
                    () -> assertEquals(author.getNationality(), author.getNationality(), "Nationalities should match"),
                    () -> assertEquals(author.getBiographyEs(), authorDto.biographyEs(), "BiographyEs should match"),
                    () -> assertEquals(author.getBiographyEn(), authorDto.biographyEn(), "BiographyEn should match"),
                    () -> assertEquals(author.getBirthYear(), authorDto.birthYear(), "Birth years should match"),
                    () -> assertEquals(author.getDeathYear(), authorDto.deathYear(), "Death years should match"),
                    () -> assertEquals(author.getSlug(), authorDto.slug(), "Slugs should match")
            );
        }

        @Test
        @DisplayName("Test map null Author to AuthorDto throws BusinessException")
        void toAuthorDto_NullAuthor_ThrowsBusinessException() {
            Author author = null;

            var exception = assertThrows(es.cesguiro.exception.BusinessException.class, () -> AuthorMapper.getInstance().fromAuthorToAuthorDto(author));

            assertEquals("Author cannot be null", exception.getMessage());
        }

        @Test
        @DisplayName("Test map List<Author> to AuthorDto")
        void toAuthorDtoList() {
            var authors = java.util.List.of(
                    new Author(1L, "name1", "nationality1", "biographyEs1", "biographyEn1", 1990, 2020, "slug1"),
                    new Author(2L, "name2", "nationality2", "biographyEs2", "biographyEn2", 1980, null, "slug2")
            );

            var authorDtos = authors.stream().map(AuthorMapper.getInstance()::fromAuthorToAuthorDto).toList();

            assertAll(
                    () -> assertEquals(2, authorDtos.size(), "There should be two author DTOs"),
                    () -> {
                        var author1 = authors.get(0);
                        var dto1 = authorDtos.get(0);
                        assertAll(
                                () -> assertEquals(author1.getId(), dto1.id(), "IDs should match"),
                                () -> assertEquals(author1.getName(), dto1.name(), "Names should match"),
                                () -> assertEquals(author1.getNationality(), dto1.nationality(), "Nationalities should match"),
                                () -> assertEquals(author1.getBiographyEs(), dto1.biographyEs(), "BiographyEs should match"),
                                () -> assertEquals(author1.getBiographyEn(), dto1.biographyEn(), "BiographyEn should match"),
                                () -> assertEquals(author1.getBirthYear(), dto1.birthYear(), "Birth years should match"),
                                () -> assertEquals(author1.getDeathYear(), dto1.deathYear(), "Death years should match"),
                                () -> assertEquals(author1.getSlug(), dto1.slug(), "Slugs should match")
                        );
                    },
                    () -> {
                        var author2 = authors.get(1);
                        var dto2 = authorDtos.get(1);
                        assertAll(
                                () -> assertEquals(author2.getId(), dto2.id(), "IDs should match"),
                                () -> assertEquals(author2.getName(), dto2.name(), "Names should match"),
                                () -> assertEquals(author2.getNationality(), dto2.nationality(), "Nationalities should match"),
                                () -> assertEquals(author2.getBiographyEs(), dto2.biographyEs(), "BiographyEs should match"),
                                () -> assertEquals(author2.getBiographyEn(), dto2.biographyEn(), "BiographyEn should match"),
                                () -> assertEquals(author2.getBirthYear(), dto2.birthYear(), "Birth years should match"),
                                () -> assertEquals(author2.getDeathYear(), dto2.deathYear(), "Death years should match"),
                                () -> assertEquals(author2.getSlug(), dto2.slug(), "Slugs should match")
                        );
                    }
            );
        }
    }

    @Nested
    @DisplayName("Testing AuthorDto to Author mapping")
    class FromAuthorDtoToAuthor {
        @Test
        @DisplayName("Test map AuthorDto to Author")
        void toAuthor() {
            var authorDto = new es.cesguiro.service.dto.AuthorDto(1L, "name", "nationality", "biographyEs", "biographyEn", 1990, 2020, "slug");

            var author = AuthorMapper.getInstance().fromAuthorDtoToAuthor(authorDto);

            assertAll(
                    () -> assertEquals(authorDto.id(), author.getId(), "IDs should match"),
                    () -> assertEquals(authorDto.name(), author.getName(), "Names should match"),
                    () -> assertEquals(authorDto.nationality(), author.getNationality(), "Nationalities should match"),
                    () -> assertEquals(authorDto.biographyEs(), author.getBiographyEs(), "BiographyEs should match"),
                    () -> assertEquals(authorDto.biographyEn(), author.getBiographyEn(), "BiographyEn should match"),
                    () -> assertEquals(authorDto.birthYear(), author.getBirthYear(), "Birth years should match"),
                    () -> assertEquals(authorDto.deathYear(), author.getDeathYear(), "Death years should match"),
                    () -> assertEquals(authorDto.slug(), author.getSlug(), "Slugs should match")
            );
        }

        @Test
        @DisplayName("Test map null AuthorDto to Author throws BusinessException")
        void toAuthor_NullAuthorDto_ThrowsBusinessException() {
            es.cesguiro.service.dto.AuthorDto authorDto = null;

            var exception = assertThrows(es.cesguiro.exception.BusinessException.class, () -> AuthorMapper.getInstance().fromAuthorDtoToAuthor(authorDto));

            assertEquals("AuthorDto cannot be null", exception.getMessage());
        }

        @Test
        @DisplayName("Test map List<AuthorDto> to Author")
        void toAuthorList() {
            var authorDtos = java.util.List.of(
                    new es.cesguiro.service.dto.AuthorDto(1L, "name1", "nationality1", "biographyEs1", "biographyEn1", 1990, 2020, "slug1"),
                    new es.cesguiro.service.dto.AuthorDto(2L, "name2", "nationality2", "biographyEs2", "biographyEn2", 1980, null, "slug2")
            );

            var authors = authorDtos.stream().map(AuthorMapper.getInstance()::fromAuthorDtoToAuthor).toList();

            assertAll(
                    () -> assertEquals(2, authors.size(), "There should be two authors"),
                    () -> {
                        var dto1 = authorDtos.get(0);
                        var author1 = authors.get(0);
                        assertAll(
                                () -> assertEquals(dto1.id(), author1.getId(), "IDs should match"),
                                () -> assertEquals(dto1.name(), author1.getName(), "Names should match"),
                                () -> assertEquals(dto1.nationality(), author1.getNationality(), "Nationalities should match"),
                                () -> assertEquals(dto1.biographyEs(), author1.getBiographyEs(), "BiographyEs should match"),
                                () -> assertEquals(dto1.biographyEn(), author1.getBiographyEn(), "BiographyEn should match"),
                                () -> assertEquals(dto1.birthYear(), author1.getBirthYear(), "Birth years should match"),
                                () -> assertEquals(dto1.deathYear(), author1.getDeathYear(), "Death years should match"),
                                () -> assertEquals(dto1.slug(), author1.getSlug(), "Slugs should match")
                        );
                    },
                    () -> {
                        var dto2 = authorDtos.get(1);
                        var author2 = authors.get(1);
                        assertAll(
                                () -> assertEquals(dto2.id(), author2.getId(), "IDs should match"),
                                () -> assertEquals(dto2.name(), author2.getName(), "Names should match"),
                                () -> assertEquals(dto2.nationality(), author2.getNationality(), "Nationalities should match"),
                                () -> assertEquals(dto2.biographyEs(), author2.getBiographyEs(), "BiographyEs should match"),
                                () -> assertEquals(dto2.biographyEn(), author2.getBiographyEn(), "BiographyEn should match"),
                                () -> assertEquals(dto2.birthYear(), author2.getBirthYear(), "Birth years should match"),
                                () -> assertEquals(dto2.deathYear(), author2.getDeathYear(), "Death years should match"),
                                () -> assertEquals(dto2.slug(), author2.getSlug(), "Slugs should match")
                        );
                    }
            );
        }
    }
}