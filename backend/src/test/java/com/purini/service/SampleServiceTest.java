package com.purini.service;

import com.purini.model.Sample;
import com.purini.repository.SampleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.data.util.Pair;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

@RunWith(SpringRunner.class)
@WebFluxTest
@AutoConfigureDataMongo
public class SampleServiceTest {

    @Autowired
    private SampleRepository repo;

    @Autowired
    private SampleService service;

    @Test
    public void testInsert() {

        Mono<Sample> addedSample = repo.deleteAll().then(Mono.defer(() -> service.upsert(newSample(), "test")));

        StepVerifier.create(addedSample)
                .expectNextMatches(f -> f.getName().equals("test-sample") && f.getId() != null)
                .verifyComplete();
    }

    @Test
    public void testGetAll() {

        Mono<Sample> addedSample = repo.deleteAll().then(Mono.defer(() -> service.upsert(newSample(), "test")));

        Flux<Sample> sampleFlux = addedSample.thenMany(Flux.defer(() -> service.getAll()));

        StepVerifier.create(sampleFlux)
                .expectNextMatches(f -> f.getName().equals("test-sample") && f.getId() != null)
                .verifyComplete();
    }

    @Test
    public void testUpsert() {

        Mono<Sample> addedSample = repo.deleteAll().then(Mono.defer(() -> service.upsert(newSample(), "test")));

        Mono<Tuple2<Sample, Pair<String, Long>>> updatedSample = addedSample.flatMap(f -> {
            Pair<String, Long> oldNameAndId = Pair.of(f.getName(), f.getId());
            f.setName("test-sample-2");
            return service.upsert(f, "testUser")
                    .zipWith(Mono.just(oldNameAndId));
        });

        StepVerifier.create(updatedSample)
                .expectNextMatches(t ->
                        t.getT1().getName().equals("test-sample-2")
                                && t.getT2().getFirst().equals("test-sample")
                                && t.getT1().getId() != null
                                && t.getT1().getId().equals(t.getT2().getSecond()))
                .verifyComplete();
    }

    private Sample newSample() {
        Sample sample = new Sample();
        sample.setName("test-sample");
        sample.setActive(true);
        return sample;
    }

}
