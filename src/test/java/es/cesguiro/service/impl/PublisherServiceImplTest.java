package es.cesguiro.service.impl;

import es.cesguiro.exception.BusinessException;
import es.cesguiro.mapper.PublisherMapper;
import es.cesguiro.model.Publisher;
import es.cesguiro.repository.AuthorRepository;
import es.cesguiro.repository.PublisherRepository;
import es.cesguiro.repository.entity.PublisherEntity;
import es.cesguiro.service.PublisherService;
import es.cesguiro.service.dto.PublisherDto;
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
class PublisherServiceImplTest {
    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherServiceImpl publisherServiceImpl;

    @Nested
    @DisplayName("Tests for create method")
    class CreatePublisherTests {
        @Test
        @DisplayName("create should return created PublisherDto")
        void create_ShouldReturnCreatedPublisherDto() {
            PublisherDto publisherDto = new PublisherDto(
                    1L,
                    "Publisher1",
                    "Country1"
            );

            when(publisherRepository.create(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

            PublisherDto createdPublisherDto = publisherServiceImpl.create(publisherDto);

            assertAll(
                    () -> assertNotNull(createdPublisherDto, "Created PublisherDto should not be null"),
                    () -> assertEquals(publisherDto.name(), createdPublisherDto.name(), "Names should match"),
                    () -> assertEquals(publisherDto.slug(), createdPublisherDto.slug(), "Countries should match")
            );
        }

        @Test
        @DisplayName("create publisher with null publisherDto should throw exception")
        void create_WithNullPublisherDto_ShouldThrowException() {
            PublisherDto publisherDto = new PublisherDto(
                    1L,
                    "Publisher1",
                    "Country1"
            );

            PublisherDto createdPublisherDto = null;

            assertThrows(BusinessException.class, () -> publisherServiceImpl.create(createdPublisherDto));
        }

        @Test
        @DisplayName("create publisher with existing slug should throw exception")
        void create_WithExistingSlug_ShouldThrowException() {
            Publisher existingPublisher = new Publisher(
                    1L,
                    "Publisher1",
                    "Country1"
            );

            PublisherDto existingPublisherDto = PublisherMapper.getInstance().fromPublisherToPublisherDto(existingPublisher);

            assertThrows(BusinessException.class, () -> publisherServiceImpl.create(existingPublisherDto));
        }
    }
}
