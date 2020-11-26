package com.purini.service;

import com.purini.model.Sample;
import com.purini.repository.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

@Service
public class SampleService {

    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private CrudService crudService;

    public Mono<Sample> getById(Long id) {
        return sampleRepository.getById(id);
    }

    public Mono<Sample> upsert(@NotNull Sample sample, @NotNull String username) {
        return crudService.upsert(sample, username, sampleRepository);
    }

    public Flux<Sample> getAll() {
        return sampleRepository.getAll();
    }
}
