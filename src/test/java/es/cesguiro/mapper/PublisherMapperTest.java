package es.cesguiro.mapper;

import es.cesguiro.model.Publisher;
import es.cesguiro.repository.entity.PublisherEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PublisherMapperTest {

    @Nested
    @DisplayName("Testing PublisherEntity to Publisher mapping")
    class PublisherEntityToPublisherTests {
        @Test
        @DisplayName("Test map PublisherEntity to Publisher")
        void toPublisher() {
            PublisherEntity publisherEntity = new PublisherEntity(1L, "name", "slug");

            var publisher = PublisherMapper.getInstance().fromPublisherEntityToPublisher(publisherEntity);

            assertAll(
                    () -> assertEquals(publisherEntity.id(), publisher.getId(), "IDs should match"),
                    () -> assertEquals(publisherEntity.name(), publisher.getName(), "Names should match"),
                    () -> assertEquals(publisherEntity.slug(), publisher.getSlug(), "Slugs should match")
            );
        }

        @Test
        @DisplayName("Test map null PublisherEntity to Publisher throws BusinessException")
        void toPublisher_NullPublisherEntity_ThrowsBusinessException() {
            PublisherEntity publisherEntity = null;

            var exception = assertThrows(es.cesguiro.exception.BusinessException.class, () -> PublisherMapper.getInstance().fromPublisherEntityToPublisher(publisherEntity));

            assertEquals("PublisherEntity cannot be null", exception.getMessage());
        }

        @Test
        @DisplayName("Test map List<PublisherEntity> to Publisher")
        void toPublisherList() {
            var publisherEntities = java.util.List.of(
                    new PublisherEntity(1L, "name1", "slug1"),
                    new PublisherEntity(2L, "name2", "slug2")
            );

            var publishers = publisherEntities.stream().map(PublisherMapper.getInstance()::fromPublisherEntityToPublisher).toList();

            assertAll(
                    () -> assertEquals(2, publishers.size(), "There should be two publishers"),
                    () -> {
                        var publisher1 = publishers.get(0);
                        var entity1 = publisherEntities.get(0);
                        assertAll(
                                () -> assertEquals(entity1.id(), publisher1.getId(), "IDs should match for first publisher"),
                                () -> assertEquals(entity1.name(), publisher1.getName(), "Names should match for first publisher"),
                                () -> assertEquals(entity1.slug(), publisher1.getSlug(), "Slugs should match for first publisher")
                        );
                    },
                    () -> {
                        var publisher2 = publishers.get(1);
                        var entity2 = publisherEntities.get(1);
                        assertAll(
                                () -> assertEquals(entity2.id(), publisher2.getId(), "IDs should match for second publisher"),
                                () -> assertEquals(entity2.name(), publisher2.getName(), "Names should match for second publisher"),
                                () -> assertEquals(entity2.slug(), publisher2.getSlug(), "Slugs should match for second publisher")
                        );
                    }
            );
        }
    }

    @Nested
    @DisplayName("Testing Publisher to PublisherEntity mapping")
    class PublisherToPublisherEntityTests {
        @Test
        @DisplayName("Test map Publisher to PublisherEntity")
        void toPublisherEntity() {
            var publisher = new Publisher(1L, "name", "slug");

            var publisherEntity = PublisherMapper.getInstance().fromPublisherToPublisherEntity(publisher);

            assertAll(
                    () -> assertEquals(publisher.getId(), publisherEntity.id(), "IDs should match"),
                    () -> assertEquals(publisher.getName(), publisherEntity.name(), "Names should match"),
                    () -> assertEquals(publisher.getSlug(), publisherEntity.slug(), "Slugs should match")
            );
        }

        @Test
        @DisplayName("Test map null Publisher to PublisherEntity throws BusinessException")
        void toPublisherEntity_NullPublisher_ThrowsBusinessException() {
            Publisher publisher = null;

            var exception = assertThrows(es.cesguiro.exception.BusinessException.class, () -> PublisherMapper.getInstance().fromPublisherToPublisherEntity(publisher));

            assertEquals("Publisher cannot be null", exception.getMessage());
        }

        @Test
        @DisplayName("Test map List<Publisher> to PublisherEntity")
        void toPublisherEntityList() {
            var publishers = java.util.List.of(
                    new Publisher(1L, "name1", "slug1"),
                    new Publisher(2L, "name2", "slug2")
            );

            var publisherEntities = publishers.stream().map(PublisherMapper.getInstance()::fromPublisherToPublisherEntity).toList();

            assertAll(
                    () -> assertEquals(2, publisherEntities.size(), "There should be two publisher entities"),
                    () -> {
                        var publisherEntity1 = publisherEntities.get(0);
                        var publisher1 = publishers.get(0);
                        assertAll(
                                () -> assertEquals(publisher1.getId(), publisherEntity1.id(), "IDs should match for first publisher entity"),
                                () -> assertEquals(publisher1.getName(), publisherEntity1.name(), "Names should match for first publisher entity"),
                                () -> assertEquals(publisher1.getSlug(), publisherEntity1.slug(), "Slugs should match for first publisher entity")
                        );
                    },
                    () -> {
                        var publisherEntity2 = publisherEntities.get(1);
                        var publisher2 = publishers.get(1);
                        assertAll(
                                () -> assertEquals(publisher2.getId(), publisherEntity2.id(), "IDs should match for second publisher entity"),
                                () -> assertEquals(publisher2.getName(), publisherEntity2.name(), "Names should match for second publisher entity"),
                                () -> assertEquals(publisher2.getSlug(), publisherEntity2.slug(), "Slugs should match for second publisher entity")
                        );
                    }
            );
        }
    }

    @Nested
    @DisplayName("Testing Publisher to PublisherDto mapping")
    class PublisherToPublisherDtoTests {
        @Test
        @DisplayName("Test map Publisher to PublisherDto")
        void toPublisherDto() {
            var publisher = new Publisher(1L, "name", "slug");

            var publisherDto = PublisherMapper.getInstance().fromPublisherToPublisherDto(publisher);

            assertAll(
                    () -> assertEquals(publisher.getId(), publisherDto.id(), "IDs should match"),
                    () -> assertEquals(publisher.getName(), publisherDto.name(), "Names should match"),
                    () -> assertEquals(publisher.getSlug(), publisherDto.slug(), "Slugs should match")
            );
        }

        @Test
        @DisplayName("Test map null Publisher to PublisherDto throws BusinessException")
        void toPublisherDto_NullPublisher_ThrowsBusinessException() {
            Publisher publisher = null;

            var exception = assertThrows(es.cesguiro.exception.BusinessException.class, () -> PublisherMapper.getInstance().fromPublisherToPublisherDto(publisher));

            assertEquals("Publisher cannot be null", exception.getMessage());
        }

        @Test
        @DisplayName("Test map List<Publisher> to PublisherDto")
        void toPublisherDtoList() {
            var publishers = java.util.List.of(
                    new Publisher(1L, "name1", "slug1"),
                    new Publisher(2L, "name2", "slug2")
            );

            var publisherDtos = publishers.stream().map(PublisherMapper.getInstance()::fromPublisherToPublisherDto).toList();

            assertAll(
                    () -> assertEquals(2, publisherDtos.size(), "There should be two publisher DTOs"),
                    () -> {
                        var publisherDto1 = publisherDtos.get(0);
                        var publisher1 = publishers.get(0);
                        assertAll(
                                () -> assertEquals(publisher1.getId(), publisherDto1.id(), "IDs should match for first publisher DTO"),
                                () -> assertEquals(publisher1.getName(), publisherDto1.name(), "Names should match for first publisher DTO"),
                                () -> assertEquals(publisher1.getSlug(), publisherDto1.slug(), "Slugs should match for first publisher DTO")
                        );
                    },
                    () -> {
                        var publisherDto2 = publisherDtos.get(1);
                        var publisher2 = publishers.get(1);
                        assertAll(
                                () -> assertEquals(publisher2.getId(), publisherDto2.id(), "IDs should match for second publisher DTO"),
                                () -> assertEquals(publisher2.getName(), publisherDto2.name(), "Names should match for second publisher DTO"),
                                () -> assertEquals(publisher2.getSlug(), publisherDto2.slug(), "Slugs should match for second publisher DTO")
                        );
                    }
            );
        }
    }

    @Nested
    @DisplayName("Testing PublisherDto to Publisher mapping")
    class PublisherDtoToPublisherTests {
        @Test
        @DisplayName("Test map PublisherDto to Publisher")
        void toPublisher() {
            var publisherDto = new es.cesguiro.service.dto.PublisherDto(1L, "name", "slug");

            var publisher = PublisherMapper.getInstance().fromPublisherDtoToPublisher(publisherDto);

            assertAll(
                    () -> assertEquals(publisherDto.id(), publisher.getId(), "IDs should match"),
                    () -> assertEquals(publisherDto.name(), publisher.getName(), "Names should match"),
                    () -> assertEquals(publisherDto.slug(), publisher.getSlug(), "Slugs should match")
            );
        }

        @Test
        @DisplayName("Test map null PublisherDto to Publisher throws BusinessException")
        void toPublisher_NullPublisherDto_ThrowsBusinessException() {
            es.cesguiro.service.dto.PublisherDto publisherDto = null;

            var exception = assertThrows(es.cesguiro.exception.BusinessException.class, () -> PublisherMapper.getInstance().fromPublisherDtoToPublisher(publisherDto));

            assertEquals("PublisherDto cannot be null", exception.getMessage());
        }

        @Test
        @DisplayName("Test map List<PublisherDto> to Publisher")
        void toPublisherList() {
            var publisherDtos = java.util.List.of(
                    new es.cesguiro.service.dto.PublisherDto(1L, "name1", "slug1"),
                    new es.cesguiro.service.dto.PublisherDto(2L, "name2", "slug2")
            );

            var publishers = publisherDtos.stream().map(PublisherMapper.getInstance()::fromPublisherDtoToPublisher).toList();

            assertAll(
                    () -> assertEquals(2, publishers.size(), "There should be two publishers"),
                    () -> {
                        var publisher1 = publishers.get(0);
                        var dto1 = publisherDtos.get(0);
                        assertAll(
                                () -> assertEquals(dto1.id(), publisher1.getId(), "IDs should match for first publisher"),
                                () -> assertEquals(dto1.name(), publisher1.getName(), "Names should match for first publisher"),
                                () -> assertEquals(dto1.slug(), publisher1.getSlug(), "Slugs should match for first publisher")
                        );
                    },
                    () -> {
                        var publisher2 = publishers.get(1);
                        var dto2 = publisherDtos.get(1);
                        assertAll(
                                () -> assertEquals(dto2.id(), publisher2.getId(), "IDs should match for second publisher"),
                                () -> assertEquals(dto2.name(), publisher2.getName(), "Names should match for second publisher"),
                                () -> assertEquals(dto2.slug(), publisher2.getSlug(), "Slugs should match for second publisher")
                        );
                    }
            );
        }    
    }
}
