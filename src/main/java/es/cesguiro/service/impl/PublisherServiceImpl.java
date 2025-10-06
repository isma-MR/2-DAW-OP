package es.cesguiro.service.impl;

import es.cesguiro.mapper.AuthorMapper;
import es.cesguiro.mapper.PublisherMapper;
import es.cesguiro.model.Author;
import es.cesguiro.model.Publisher;
import es.cesguiro.repository.BookRepository;
import es.cesguiro.repository.PublisherRepository;
import es.cesguiro.repository.entity.AuthorEntity;
import es.cesguiro.repository.entity.PublisherEntity;
import es.cesguiro.service.dto.PublisherDto;
import es.cesguiro.service.PublisherService;

import java.util.List;

public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public List<PublisherDto> getAll() {
        return List.of();
    }

    @Override
    public PublisherDto getBySlug(String slug) {
        return null;
    }

    @Override
    public PublisherDto create(PublisherDto publisherDto) {
        Publisher publisher = PublisherMapper.getInstance().fromPublisherDtoToPublisher(publisherDto);
        PublisherEntity publisherEntity = PublisherMapper.getInstance().fromPublisherToPublisherEntity(publisher);
        PublisherEntity newPublisherEntity = publisherRepository.create(publisherEntity);
        Publisher newPublisher = PublisherMapper.getInstance().fromPublisherEntityToPublisher(newPublisherEntity);
        return PublisherMapper.getInstance().fromPublisherToPublisherDto(newPublisher);
    }

    @Override
    public PublisherDto update(PublisherDto publisherDto) {
        return null;
    }

    @Override
    public int delete(String slug) {
        return 0;
    }
}
