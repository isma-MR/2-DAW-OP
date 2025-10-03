package es.cesguiro.service.impl;

import es.cesguiro.exception.BusinessException;
import es.cesguiro.mapper.BookMapper;
import es.cesguiro.model.Author;
import es.cesguiro.model.Book;
import es.cesguiro.model.Publisher;
import es.cesguiro.repository.BookRepository;
import es.cesguiro.repository.entity.AuthorEntity;
import es.cesguiro.repository.entity.BookEntity;
import es.cesguiro.repository.entity.PublisherEntity;
import es.cesguiro.service.dto.BookDto;
import es.cesguiro.service.dto.PublisherDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookServiceImpl;

    // Mock books
    List<BookEntity> bookEntities = List.of(
        new BookEntity(
            1L,
            "123",
            "TitleEs1",
            "TitleEn1",
            "SynopsisEs1",
            "SynopsisEn1",
            new BigDecimal("10.00"),
            5,
            "cover1.jpg",
            LocalDate.of(2020, 1, 1),

            new PublisherEntity(1L,"Publisher1", "publisher1-slug"),
            List.of(new AuthorEntity(1L, "Author1", "Country1", "BioEs1", "BioEn1", 1970, null, "author1-slug"))

        ),
        new BookEntity(
            2L,
            "456",
            "TitleEs2",
            "TitleEn2",
            "SynopsisEs2",
            "SynopsisEn2",
            new BigDecimal("15.00"),
            10,
            "cover2.jpg",
            LocalDate.of(2021, 6, 15),
            new PublisherEntity(2L,"Publisher2", "publisher2-slug"),
            List.of(new AuthorEntity(2L, "Author2", "Country2", "BioEs2", "BioEn2", 1950, null, "author2-slug"))
        ),
        new BookEntity(
            3L,
            "789",
            "TitleEs3",
            "TitleEn3",
            "SynopsisEs3",
            "SynopsisEn3",
            new BigDecimal("20.50"),
            3,
            "cover3.png",
            LocalDate.of(2019, 3, 10),
            new PublisherEntity(3L,"Publisher3", "publisher3-slug"),
            List.of(new AuthorEntity(3L, "Author3", "Country3", "BioEs3", "BioEn3", 1985, null, "author3-slug"))

        ),
        new BookEntity(
            4L,
            "101112",
            "TitleEs4",
            "TitleEn4",
            "SynopsisEs4",
            "SynopsisEn4",
            new BigDecimal("8.75"),
            7,
            "cover4.png",
            LocalDate.of(2018, 11, 5),
            new PublisherEntity(4L,"Publisher4", "publisher4-slug"),
            null
        ),
        new BookEntity(
            5L,
            "131415",
            "TitleEs5",
            "TitleEn5",
            "SynopsisEs5",
            "SynopsisEn5",
            new BigDecimal("30.00"),
            2,
            "cover5.jpg",
            LocalDate.of(2022, 8, 20),
            new PublisherEntity(5L,"Publisher5", "publisher5-slug"),
            List.of()
        )
    );

    @Nested
    class GetAllTests {
        @Test
        @DisplayName("getAll should return list of books")
        void getAll_ShouldReturnListOfBooks() {
            // Arrange
            int page = 0;
            int size = 10;
            boolean validSize;
            when(bookRepository.findAll(page, size)).thenReturn(bookEntities);


            // Act
            List<BookDto> result = bookServiceImpl.getAll(page, size);
            validSize = result.size() <= size;
            // Assert
            assertAll(
                    () -> assertNotNull(result, "Result should not be null"),
                    () -> assertEquals(bookEntities.size(), result.size(), "Result size should be " + bookEntities.size()),
                    () -> assertTrue(validSize, "Result size should be less than or equal to the requested size"),
                    () -> assertEquals("123", result.get(0).isbn(), "First book ISBN should match"),
                    () -> assertEquals("456", result.get(1).isbn(), "Second book ISBN should match"),
                    () -> assertEquals("789", result.get(2).isbn(), "Third book ISBN should match"),
                    () -> assertEquals("101112", result.get(3).isbn(), "Fourth book ISBN should match"),
                    () -> assertEquals("131415", result.get(4).isbn(), "Fifth book ISBN should match")
            );

            // Verify interaction with mock
            Mockito.verify(bookRepository).findAll(page, size);
        }

        @Test
        @DisplayName("getAll without books should throw exception")
        void getAll_WithoutBooks_ShouldThrowException(){
            // Arrange
            int page = 0;
            int size = 10;

            List<BookEntity> bookEntities = null;
            when(bookRepository.findAll(page, size)).thenReturn(bookEntities);

            // Act & Assert
            assertThrows(es.cesguiro.exception.BusinessException.class, () -> bookServiceImpl.getAll(page, size));

            // Verify interaction with mock
            Mockito.verify(bookRepository).findAll(page, size);
        }
    }

    @Nested
    class GetByIsbnTests{
        @Test
        @DisplayName("Given valid isbn should return book")
        void getByIsbn_ValidIsbn_ShouldReturnBook() {
            // Arrange
            String isbn = "123";

            when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.of(bookEntities.get(0)));

            // Act

            BookDto result = bookServiceImpl.getByIsbn(isbn);

            // Assert
            assertAll(
                    () -> assertNotNull(result, "Result should not be null"),
                    () -> assertEquals(isbn, result.isbn(), "ISBN should match"),
                    () -> assertEquals("TitleEs1", result.titleEs(), "TitleEs should match"),
                    () -> assertEquals("TitleEn1", result.titleEn(), "TitleEn should match"),
                    () -> assertEquals("SynopsisEs1", result.synopsisEs(), "SynopsisEs should match"),
                    () -> assertEquals("SynopsisEn1", result.synopsisEn(), "SynopsisEn should match"),
                    () -> assertEquals(new BigDecimal("10.00"), result.basePrice(), "BasePrice should match"),
                    () -> assertEquals(5, result.discountPercentage(), "DiscountPercentage should match"),
                    () -> assertEquals("cover1.jpg", result.cover(), "Cover should match"),
                    () -> assertEquals(LocalDate.of(2020, 1, 1), result.publicationDate(), "PublicationDate should match"),
                    () -> assertNotNull(result.publisher(), "Publisher should not be null"),
                    () -> assertEquals("Publisher1", result.publisher().name(), "Publisher name should match"),
                    () -> assertEquals("publisher1-slug", result.publisher().slug(), "Publisher slug should match"),
                    () -> assertNotNull(result.authors(), "Authors should not be null"),
                    () -> assertEquals(1, result.authors().size(), "Authors size should be 1"),
                    () -> assertEquals("Author1", result.authors().get(0).name(), "Author name should match"),
                    () -> assertEquals("Country1", result.authors().get(0).nationality(), "Author nationality should match"),
                    () -> assertEquals("BioEs1", result.authors().get(0).biographyEs(), "Author biographyEs should match"),
                    () -> assertEquals("BioEn1", result.authors().get(0).biographyEn(), "Author biographyEn should match"),
                    () -> assertEquals(1970, result.authors().get(0).birthYear(), "Author birthYear should match"),
                    () -> assertNull(result.authors().get(0).deathYear(), "Author deathYear should be null"),
                    () -> assertEquals("author1-slug", result.authors().get(0).slug(), "Author slug should match")
            );
        }
        @Test
        @DisplayName("Given invalid isbn should throw exception")
        void getByIsbn_InvalidIsbn_ShouldThrowException() {
            // Arrange
            String isbn = "999";
            when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(es.cesguiro.exception.BusinessException.class, () -> bookServiceImpl.getByIsbn(isbn));
        }
    }

    @Nested
    class CreateBookTests {
        @Test
        @DisplayName("Given valid book should create book")
        void create_ValidBook_ShouldCreateBook() {
            // Arrange
            Book newBook = new Book(
                    1L,
                    "999",
                    "NewTitleEs",
                    "NewTitleEn",
                    "NewSynopsisEs",
                    "NewSynopsisEn",
                    new BigDecimal("25.00"),
                    15,
                    "newcover.jpg",
                    LocalDate.of(2023, 5, 1),
                    new Publisher(1L,"NewPublisher", "newpublisher-slug"),
                    List.of(new Author(1L, "NewAuthor", "NewCountry", "NewBioEs", "NewBioEn", 1980, null, "newauthor-slug"))
            );
            BookDto newBookDto = BookMapper.getInstance().fromBookToBookDto(newBook);
            BookEntity newBookEntity = BookMapper.getInstance().fromBookToBookEntity(newBook);

            when(bookRepository.findByIsbn(newBook.getIsbn())).thenReturn(Optional.empty());
            when(bookRepository.save(Mockito.any(BookEntity.class))).thenReturn(newBookEntity);

            // Act
            BookDto createdBook = bookServiceImpl.create(newBookDto);

            // Assert
            assertAll(
                    () -> assertNotNull(createdBook, "Created book should not be null"),
                    () -> assertEquals(newBook.getIsbn(), createdBook.isbn(), "ISBN should match"),
                    () -> assertEquals(newBook.getTitleEs(), createdBook.titleEs(), "TitleEs should match"),
                    () -> assertEquals(newBook.getTitleEn(), createdBook.titleEn(), "TitleEn should match"),
                    () -> assertEquals(newBook.getSynopsisEs(), createdBook.synopsisEs(), "SynopsisEs should match"),
                    () -> assertEquals(newBook.getSynopsisEn(), createdBook.synopsisEn(), "SynopsisEn should match"),
                    () -> assertEquals(newBook.getBasePrice(), createdBook.basePrice(), "BasePrice should match"),
                    () -> assertEquals(newBook.getDiscountPercentage(), createdBook.discountPercentage(), "DiscountPercentage should match"),
                    () -> assertEquals(newBook.getCover(), createdBook.cover(), "Cover should match"),
                    () -> assertEquals(newBook.getPublicationDate(), createdBook.publicationDate(), "PublicationDate should match"),
                    () -> assertNotNull(createdBook.publisher(), "Publisher should not be null"),
                    () -> assertEquals(newBook.getPublisher().getName(), createdBook.publisher().name(), "Publisher name should match"),
                    () -> assertEquals(newBook.getPublisher().getSlug(), createdBook.publisher().slug(), "Publisher slug should match"),
                    () -> assertNotNull(createdBook.authors(), "Authors should not be null"),
                    () -> assertEquals(1, createdBook.authors().size(), "Authors size should be 1"),
                    () -> assertEquals(newBook.getAuthors().get(0).getName(), createdBook.authors().get(0).name(), "Author name should match"),
                    () -> assertEquals(newBook.getAuthors().get(0).getNationality(), createdBook.authors().get(0).nationality(), "Author nationality should match"),
                    () -> assertEquals(newBook.getAuthors().get(0).getBiographyEs(), createdBook.authors().get(0).biographyEs(), "Author biographyEs should match"),
                    () -> assertEquals(newBook.getAuthors().get(0).getBiographyEn(), createdBook.authors().get(0).biographyEn(), "Author biographyEn should match"),
                    () -> assertEquals(newBook.getAuthors().get(0).getBirthYear(), createdBook.authors().get(0).birthYear(), "Author birthYear should match"),
                    () -> assertNull(createdBook.authors().get(0).deathYear(), "Author deathYear should be null"),
                    () -> assertEquals(newBook.getAuthors().get(0).getSlug(), createdBook.authors().get(0).slug(), "Author slug should match")
            );
        }

        @Test
        @DisplayName("Given existing book should throws exception")
        void create_ExistingBook_ShouldThrowException() {
            Book existingBook =  new Book (
                    1L,
                    "123",
                    "TituloExistenteEs",
                    "TituloExistenteEn",
                    "SinopsisExistenteEs",
                    "SinopsisExistenteEn",
                    new BigDecimal("12.00"),
                    10,
                    "coverexistente.jpg",
                    LocalDate.of(2022, 1, 1),
                    new Publisher(1L,"EditorialExistente", "editorialexistente-slug"),
                    List.of(new Author(1L, "AutorExistente", "PaisExistente", "BioEs", "BioEn", 1980, null, "autorexistente-slug"))
            );
            BookDto existingBookDto = BookMapper.getInstance().fromBookToBookDto(existingBook);
            BookEntity existingBookEntity = BookMapper.getInstance().fromBookToBookEntity(existingBook);

            when(bookRepository.findByIsbn(existingBook.getIsbn())).thenReturn(Optional.of(existingBookEntity));

            assertThrows(BusinessException.class, () -> bookServiceImpl.create(existingBookDto));
        }
        @Test
        @DisplayName("Given book with enpty list actors should throw exception")
        void create_BookWithEmptyListAuthors_ShouldThrowException() {
            BookDto bookDto = new BookDto(
                    1L,
                    "999",
                    "NewTitleEs",
                    "NewTitleEn",
                    "NewSynopsisEs",
                    "NewSynopsisEn",
                    new BigDecimal("25.00"),
                    0,
                    new BigDecimal("25.00"),
                    "newcover.jpg",
                    LocalDate.of(2022, 1, 1),
                    new PublisherDto(1L,"NewPublisher", "newpublisher-slug"),
                    List.of()
            );

            assertThrows(IllegalArgumentException.class, () -> bookServiceImpl.create(bookDto));
        }

        @Test
        @DisplayName("Given book with null list actors should throw exception")
        void create_BookWithNullListAuthors_ShouldThrowException() {
            BookDto bookDto = new BookDto(
                    1L,
                    "999",
                    "NewTitleEs",
                    "NewTitleEn",
                    "NewSynopsisEs",
                    "NewSynopsisEn",
                    new BigDecimal("25.00"),
                    0,
                    new BigDecimal("25.00"),
                    "newcover.jpg",
                    LocalDate.of(2022, 1, 1),
                    new PublisherDto(1L,"NewPublisher", "newpublisher-slug"),
                    null
            );

            assertThrows(IllegalArgumentException.class, () -> bookServiceImpl.create(bookDto));
        }
    }
}