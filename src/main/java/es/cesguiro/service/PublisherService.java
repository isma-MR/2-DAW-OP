package es.cesguiro.service;

import es.cesguiro.service.dto.PublisherDto;

import java.util.List;

public interface PublisherService {

    List<PublisherDto> getAll();

    PublisherDto getBySlug(String slug);

    PublisherDto create(PublisherDto publisherDto);

    PublisherDto update(PublisherDto publisherDto);

    PublisherDto delete(String slug);
}
