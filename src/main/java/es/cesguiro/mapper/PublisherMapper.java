package es.cesguiro.mapper;

import es.cesguiro.exception.BusinessException;
import es.cesguiro.model.Publisher;
import es.cesguiro.repository.entity.PublisherEntity;
import es.cesguiro.service.dto.PublisherDto;

public class PublisherMapper {

    private static PublisherMapper INSTANCE;

    private PublisherMapper() {
    }

    public static PublisherMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PublisherMapper();
        }
        return INSTANCE;
    }

    public Publisher fromPublisherEntityToPublisher(PublisherEntity publisherEntity) {
        if (publisherEntity == null) {
            return null;
        }
        return new Publisher(
                publisherEntity.name(),
                publisherEntity.slug()
        );
    }

    public PublisherEntity fromPublisherToPublisherEntity(Publisher publisher) {
        if (publisher == null) {
            return null;
        }
        return new PublisherEntity(
                publisher.getName(),
                publisher.getSlug()
        );
    }

    public PublisherDto fromPublisherToPublisherDto(Publisher publisher) {
        if (publisher == null) {
            return null;
        }
        return new PublisherDto(
                publisher.getName(),
                publisher.getSlug()
        );
    }

    public Publisher fromPublisherDtoToPublisher(PublisherDto publisherDto) {
        if (publisherDto == null) {
            return null;
        }
        return new Publisher(
                publisherDto.name(),
                publisherDto.slug()
        );
    }
}
