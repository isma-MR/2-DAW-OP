# DAW2 - Bookstore Domain

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Este proyecto es una implementación de la **capa de dominio** en una arquitectura limpia de una aplicación de ventas de libros online, creada con fines **didácticos** para un ciclo de formación profesional en programación web. En este repositorio encontrarás ejemplos de entidades, repositorios, servicios y mapeadores de la capa de dominio.

Es solo una parte de una aplicación más grande y no es funcional por sí solo. Está diseñado para ilustrar cómo estructurar la capa de dominio en una arquitectura limpia. No incluye la capa de persistencia, la capa de presentación ni la lógica de negocio completa.


## ⚠️ Nota importante


**Hay tests unitarios que fallan intencionadamente para que los alumnos aprendan a desarrollar utilizando la filosofía TDD (Test-Driven Development).**

## Versiones
- 0.0.1-SNAPSHOT: Versión en desarrollo. Implementado varios servicios.

## Estructura del Proyecto

El proyecto está organizado en distintas capas, cada una con sus respectivos modelos y responsabilidades.

### Modelos de Dominio

- **Book**
    - isbn: Código ISBN del libro
    - titleEs: Título del libro en español
    - titleEn: Título del libro en inglés
    - synopsisEs: Sinopsis del libro en español
    - synopsisEn: Sinopsis del libro en inglés
    - basePrice: Precio base del libro
    - discount: Descuento aplicado al libro
    - finalPrice: Precio final tras aplicar el descuento
    - cover: Imagen de la portada
    - publicationDate: Fecha de publicación
    - publisher: Editorial del libro
    - authors: Autores asociados al libro

- **Author**:
    - name: Nombre del autor
    - nationality: Nacionalidad del autor
    - biographyEs: Biografía del autor en español
    - biographyEn: Biografía del autor en inglés
    - birthYear: Año de nacimiento del autor
    - deathYear: Año de fallecimiento (opcional)
    - slug: Identificador único del autor
  
- **Publisher**
    - name: Nombre de la editorial
    - slug: Identificador único de la editorial

### Servicios

```java
public interface BookService {

    List<BookDto> findAll(int page, int size);

    BookDto findByIsbn(String isbn);

    BookDto create(BookDto bookDto);

    BookDto update(BookDto bookDto);

    void delete(String isbn);

}

public interface AuthorService {

    List<AuthorDto> findAll();

    AuthorDto findBySlug(String slug);

    AuthorDto create(AuthorDto authorDto);

    AuthorDto update(AuthorDto authorDto);

    int delete(String slug);
}

public interface PublisherService {

    List<PublisherDto> findAll();

    PublisherDto findBySlug(String slug);

    PublisherDto create(PublisherDto publisherDto);

    PublisherDto update(PublisherDto publisherDto);

    PublisherDto delete(String slug);
}
```

#### Modelos de los servicios

- **BookDTO**
```java
public record BookDto(
        String isbn,
        String titleEs,
        String titleEn,
        String synopsisEs,
        String synopsisEn,
        BigDecimal basePrice,
        double discountPercentage,
        BigDecimal price,
        String cover,
        LocalDate publicationDate,
        PublisherDto publisher,
        List<AuthorDto> authors
) {
}
```

- **AuthorDTO**
```java
public record AuthorDto(
        String name,
        String nationality,
        String biographyEs,
        String biographyEn,
        int birthYear,
        Integer deathYear,
        String slug
) {
}
```

- **PublisherDTO**
```java
public record PublisherDto(
        String name,
        String slug
) {
}
```

### Servicios

- Cada servicio tiene su implementación correspondiente:

```java
public class BookServiceImpl implements BookService {
    // Implementación de los métodos del servicio
}

public class AuthorServiceImpl implements AuthorService {
    // Implementación de los métodos del servicio
}

public class PublisherServiceImpl implements PublisherService {
    // Implementación de los métodos del servicio
}
```


### Repositorios

```java
public interface AuthorRepository {

  List<AuthorEntity> findAllByBookIsbn(String isbn);
}

public interface BookRepository {

  List<BookEntity> findAll(int page, int size);
}

public interface CategoryRepository {

  Optional<CategoryEntity> findByBookIsbn(String isbn);
}

public interface GenreRepository {

  List<GenreEntity> findAllByBookIsbn(String isbn);
}

public interface PublisherRepository {

  Optional<PublisherEntity> findByBookIsbn(String isbn);
}
```



#### Modelos de los repositorios

- **AuthorEntity**
```java
public record AuthorEntity(
        String name,
        String nationality,
        String biographyEs,
        String biographyEn,
        int birthYear,
        Integer deathYear,
        String slug
) {

}
```

- **BookEntity**
```java
public record BookEntity(
        String isbn,
        String titleEs,
        String titleEn,
        String synopsisEs,
        String synopsisEn,
        BigDecimal basePrice,
        double discountPercentage,
        String cover,
        LocalDate publicationDate,
        PublisherEntity publisher,
        List<AuthorEntity> authors
) {
}
```

- **PublisherEntity**
```java
public record PublisherEntity(
        String name,
        String slug
) {
}
```

### Ejecutar el Proyecto

- Clonar el repositorio:

```bash
git clone https://github.com/cesguiro/daw2-bookstore-domain.git
```

- Compilar el proyecto:
```bash
mvn clean install
```# 2-DAW-OP
