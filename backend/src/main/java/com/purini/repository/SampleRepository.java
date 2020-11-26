package com.purini.repository;

import com.purini.model.Sample;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SampleRepository extends ReactiveMongoRepository<Sample, Long>, Sequenced {

    @Override
    default String getSequenceName() {
        return "sample_seq";
    }

    @Query(value = "{ $and: [ { 'id': ?0 }, { 'active': true } ] }")
    Mono<Sample> getById(Long id);

    @Query(value = "{ 'active': true }")
    Flux<Sample> getAll();
}
