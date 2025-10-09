package es.cesguiro.mapper;

import es.cesguiro.exception.BusinessException;
import es.cesguiro.model.Author;
import es.cesguiro.model.Book;
import es.cesguiro.model.Publisher;
import es.cesguiro.repository.entity.AuthorEntity;
import es.cesguiro.repository.entity.BookEntity;
import es.cesguiro.repository.entity.PublisherEntity;
import es.cesguiro.service.dto.AuthorDto;
import es.cesguiro.service.dto.BookDto;
import es.cesguiro.service.dto.PublisherDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {
    @Nested
    @DisplayName("Test mapping from BookEntity to Book")
    class FromBookEntityToBook {
        @Test
        @DisplayName("Test BookEntity to Book mapping")
        void toBook_BookEntityToBookMapping() {
            // Arrange
            BookEntity bookEntity = new BookEntity(
                    1L,
                    "978-84-376-0494-7",
                    "TitleEs",
                    "TitleEn",
                    "SynopsisEs",
                    "SynopsisEn",
                    new BigDecimal("20.00"),
                    10,
                    "cover.jpg",
                    LocalDate.of(2020, 1, 1),
                    new PublisherEntity(1L,"Publisher Name", "publisher-slug"),
                    List.of(
                            new AuthorEntity(1L, "Author One", "nationality1", "Bio1", "Bio1En", 1970, null, "author-one"),
                            new AuthorEntity(2L, "Author Two", "nationality2", "Bio2", "Bio2En", 1980, 2020, "author-two")
                    )
            );
            // Act
            Book book = BookMapper.getInstance().fromBookEntityToBook(bookEntity);
            // Assert
            assertAll(
                    () -> assertNotNull(book, "Mapped Book should not be null"),
                    () -> assertNotNull(book.getAuthors(), "Authors list should not be null"),
                    () -> assertEquals(2, book.getAuthors().size(), "Authors list size should match"),
                    () -> assertEquals("Author One", book.getAuthors().get(0).getName(), "First author name should match"),
                    () -> assertEquals("Author Two", book.getAuthors().get(1).getName(), "Second author name should match"),
                    () -> assertNotNull(book.getPublisher(), "Publisher should not be null"),
                    () -> assertEquals("Publisher Name", book.getPublisher().getName(), "Publisher name should match"),
                    () -> assertEquals("publisher-slug", book.getPublisher().getSlug(), "Publisher slug should match"),
                    () -> assertEquals(new BigDecimal("18.00"), book.getPrice(), "Final price should be correctly calculated"),
                    () -> assertEquals(bookEntity.id(), book.getId(), "ID should match"),
                    () -> assertEquals(bookEntity.isbn(), book.getIsbn(), "ISBN should match"),
                    () -> assertEquals(bookEntity.titleEs(), book.getTitleEs(), "TitleEs should match"),
                    () -> assertEquals(bookEntity.titleEn(), book.getTitleEn(), "TitleEn should match"),
                    () -> assertEquals(bookEntity.synopsisEs(), book.getSynopsisEs(), "SynopsisEs should match"),
                    () -> assertEquals(bookEntity.synopsisEn(), book.getSynopsisEn(), "SynopsisEn should match"),
                    () -> assertEquals(bookEntity.basePrice(), book.getBasePrice(), "BasePrice should match"),
                    () -> assertEquals(bookEntity.discountPercentage(), book.getDiscountPercentage(), "DiscountPercentage should match"),
                    () -> assertEquals(bookEntity.cover(), book.getCover(), "Cover should match"),
                    () -> assertEquals(bookEntity.publicationDate(), book.getPublicationDate(), "PublicationDate should match")
            );
        }

        @Test
        @DisplayName("Test map null BookEntity to Book returns null")
        void toBook_NullBookEntity_ReturnsNull() {
            // Arrange
            BookEntity bookEntity = null;
            // Assert
            assertThrows(BusinessException.class, () -> BookMapper.getInstance().fromBookEntityToBook(bookEntity), "Mapping null BookEntity should return null Book");
        }
    }

    @Nested
    @DisplayName("Test mapping from Book to BookEntity")
    class FromBookToBookEntity {
        @Test
        @DisplayName("Test map Book to BookEntity")
        void toBookEntity_BookToBookEntity() {
            // Arrange
            Book book = new Book(
                    1L,
                    "978-84-376-0494-7",
                    "TitleEs",
                    "TitleEn",
                    "SynopsisEs",
                    "SynopsisEn",
                    new BigDecimal("20.00"),
                    10,
                    "cover.jpg",
                    LocalDate.of(2020, 1, 1),
                    null,
                    null
            );
            book.setPublisher(new Publisher(1L, "Publisher Name", "publisher-slug"));
            book.setAuthors(List.of(
                    new Author(1L, "Author One", "nationality1", "Bio1", "Bio1En", 1970, null, "author-one"),
                    new Author(2L, "Author Two", "nationality2", "Bio2", "Bio2En", 1980, 2020, "author-two")
            ));
            // Act
            BookEntity bookEntity = BookMapper.getInstance().fromBookToBookEntity(book);
            // Assert
            assertAll(
                    () -> assertEquals(book.getId(), bookEntity.id(), "ID should match"),
                    () -> assertEquals(book.getIsbn(), bookEntity.isbn(), "ISBN should match"),
                    () -> assertEquals(book.getTitleEs(), bookEntity.titleEs(), "TitleEs should match"),
                    () -> assertEquals(book.getTitleEn(), bookEntity.titleEn(), "TitleEn should match"),
                    () -> assertEquals(book.getSynopsisEs(), bookEntity.synopsisEs(), "SynopsisEs should match"),
                    () -> assertEquals(book.getSynopsisEn(), bookEntity.synopsisEn(), "SynopsisEn should match"),
                    () -> assertEquals(book.getBasePrice(), bookEntity.basePrice(), "BasePrice should match"),
                    () -> assertEquals(book.getDiscountPercentage(), bookEntity.discountPercentage(), "DiscountPercentage should match"),
                    () -> assertEquals(book.getCover(), bookEntity.cover(), "Cover should match"),
                    () -> assertEquals(book.getPublicationDate(), bookEntity.publicationDate(), "PublicationDate should match"),
                    () -> assertNotNull(bookEntity.publisher(), "Publisher should not be null"),
                    () -> assertEquals(book.getPublisher().getName(), bookEntity.publisher().name(), "Publisher name should match"),
                    () -> assertEquals(book.getPublisher().getSlug(), bookEntity.publisher().slug(), "Publisher slug should match"),
                    () -> assertNotNull(bookEntity.authors(), "Authors should not be null"),
                    () -> assertEquals(2, bookEntity.authors().size(), "Authors size should match"),
                    () -> assertEquals("Author One", bookEntity.authors().get(0).name(), "First author name should match"),
                    () -> assertEquals("Author Two", bookEntity.authors().get(1).name(), "Second author name should match")
            );
        }

        @Test
        @DisplayName("Test map null Book to BookEntity should throw BusinessException")
        void toBookEntity_NullBook_ShoudThrowBusinessException() {
            // Arrange
            Book book = null;
            // Act & Assert
            assertThrows(BusinessException.class, () -> BookMapper.getInstance().fromBookToBookEntity(book), "Mapping null Book should throw BusinessException");
        }
    }

    @Nested
    @DisplayName("Test mapping from Book to BookDto")
    class FromBookToBookDto {
        @Test
        @DisplayName("Test map Book to BookDto")
        void toBookDto() {
            // Arrange
            var book = new Book(
                    1L,
                    "978-84-376-0494-7",
                    "TitleEs",
                    "TitleEn",
                    "SynopsisEs",
                    "SynopsisEn",
                    new BigDecimal("20.00"),
                    10,
                    "cover.jpg",
                    LocalDate.of(2020, 1, 1),
                    null,
                    null
            );
            book.setPublisher(new Publisher(1L,"Publisher Name", "publisher-slug"));
            book.setAuthors(List.of(
                    new Author(1L, "Author One", "nationality1", "Bio1", "Bio1En", 1970, null, "author-one"),
                    new Author(2L, "Author Two", "nationality2", "Bio2", "Bio2En", 1980, 2020, "author-two")
            ));

            // Act
            var bookDto = BookMapper.getInstance().fromBookToBookDto(book);

            // Assert
            assertAll(
                    () -> assertEquals(book.getId(), bookDto.id(), "ID should match"),
                    () -> assertEquals(book.getIsbn(), bookDto.isbn(), "ISBN should match"),
                    () -> assertEquals(book.getTitleEs(), bookDto.titleEs(), "TitleEs should match"),
                    () -> assertEquals(book.getTitleEn(), bookDto.titleEn(), "TitleEn should match"),
                    () -> assertEquals(book.getSynopsisEs(), bookDto.synopsisEs(), "SynopsisEs should match"),
                    () -> assertEquals(book.getSynopsisEn(), bookDto.synopsisEn(), "SynopsisEn should match"),
                    () -> assertEquals(book.getBasePrice(), bookDto.basePrice(), "BasePrice should match"),
                    () -> assertEquals(book.getDiscountPercentage(), bookDto.discountPercentage(), "DiscountPercentage should match"),
                    () -> assertEquals(book.getCover(), bookDto.cover(), "Cover should match"),
                    () -> assertEquals(book.getPublicationDate(), bookDto.publicationDate(), "PublicationDate should match"),
                    () -> assertNotNull(bookDto.publisher(), "Publisher should not be null"),
                    () -> assertEquals(book.getPublisher().getName(), bookDto.publisher().name(), "Publisher name should match"),
                    () -> assertEquals(book.getPublisher().getSlug(), bookDto.publisher().slug(), "Publisher slug should match"),
                    () -> assertNotNull(bookDto.authors(), "Authors should not be null"),
                    () -> assertEquals(2, bookDto.authors().size(), "Authors size should match"),
                    () -> assertEquals("Author One", bookDto.authors().get(0).name(), "First author name should match"),
                    () -> assertEquals("Author Two", bookDto.authors().get(1).name(), "Second author name should match")
            );
        }

        @Test
        @DisplayName("Test map null Book to BookDto should throw BusinessException")
        void toBookDto_NullBook_ShoudThrowBusinessException() {
            Book book = null;

            assertThrows(BusinessException.class, () -> BookMapper.getInstance().fromBookToBookDto(book), "Mapping null Book should throw BusinessException");
        }
    }

    @Nested
    @DisplayName("Test mapping from BookDto to Book")
    class FromBookDtoToBook {
        @Test
        @DisplayName("Test map BookDto to Book")
        void toBook_BookDtoToBook() {
            // Arrange
            var bookDto = new BookDto(
                    1L,
                    "978-84-376-0494-7",
                    "TitleEs",
                    "TitleEn",
                    "SynopsisEs",
                    "SynopsisEn",
                    new BigDecimal("20.00"),
                    10,
                    new BigDecimal("18.00"),
                    "cover.jpg",
                    LocalDate.of(2020, 1, 1),
                    new PublisherDto(1L,"Publisher Name", "publisher-slug"),
                    List.of(
                            new AuthorDto(1L, "Author One", "nationality1", "Bio1", "Bio1En", 1970, null, "author-one"),
                            new AuthorDto(2L, "Author Two", "nationality2", "Bio2", "Bio2En", 1980, 2020, "author-two")
                    )
            );

            // Act
            Book book = BookMapper.getInstance().fromBookDtoToBook(bookDto);

            // Assert
            assertAll(
                    () -> assertEquals(bookDto.id(), book.getId(), "ID should match"),
                    () -> assertEquals(bookDto.isbn(), book.getIsbn(), "ISBN should match"),
                    () -> assertEquals(bookDto.titleEs(), book.getTitleEs(), "TitleEs should match"),
                    () -> assertEquals(bookDto.titleEn(), book.getTitleEn(), "TitleEn should match"),
                    () -> assertEquals(bookDto.synopsisEs(), book.getSynopsisEs(), "SynopsisEs should match"),
                    () -> assertEquals(bookDto.synopsisEn(), book.getSynopsisEn(), "SynopsisEn should match"),
                    () -> assertEquals(bookDto.basePrice(), book.getBasePrice(), "BasePrice should match"),
                    () -> assertEquals(bookDto.discountPercentage(), book.getDiscountPercentage(), "DiscountPercentage should match"),
                    () -> assertEquals(bookDto.cover(), book.getCover(), "Cover should match"),
                    () -> assertEquals(bookDto.publicationDate(), book.getPublicationDate(), "PublicationDate should match"),
                    () -> assertNotNull(book.getPublisher(), "Publisher should not be null"),
                    () -> assertEquals(bookDto.publisher().name(), book.getPublisher().getName(), "Publisher name should match"),
                    () -> assertEquals(bookDto.publisher().slug(), book.getPublisher().getSlug(), "Publisher slug should match"),
                    () -> assertNotNull(book.getAuthors(), "Authors should not be null"),
                    () -> assertEquals(2, book.getAuthors().size(), "Authors size should match"),
                    () -> assertEquals("Author One", book.getAuthors().get(0).getName(), "First author name should match"),
                    () -> assertEquals("Author Two", book.getAuthors().get(1).getName(), "Second author name should match")
            );
        }

        @Test
        @DisplayName("Test map null BookDto to Book throws BusinessException")
        void toBook_NullBookDto_ThrowsBusinessException() {
            // Arrange
            BookDto bookDto = null;
            // Act & Assert
            assertThrows(BusinessException.class, () -> BookMapper.getInstance().fromBookDtoToBook(bookDto));
        }
    }
}