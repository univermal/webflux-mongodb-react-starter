package com.purini.service;

import com.purini.model.Identifiable;
import com.purini.model.pojos.Auditable;
import com.purini.repository.Sequenced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Service
public class CrudService {

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private SampleService sampleService;

    public <R extends ReactiveMongoRepository<T, Long> & Sequenced, T extends Identifiable & Auditable>
    Mono<T> upsert(T t, String username, R repository) {
        Date date = new Date();
        return Mono.just(t)
                .zipWith(Mono.justOrEmpty(t.getId() == null ? Mono.<T>empty() : repository.findById(t.getId())))
                .flatMap(tu ->
                        withOldId(tu.getT1(), tu.getT2())
                                .switchIfEmpty(Mono.defer(() -> Mono.just(withNewId(tu.getT1(), repository)))))
                .flatMap(s -> save(s, username, date, repository))
                .onErrorMap(e -> new RuntimeException("Error in saving, cause: " + e.getMessage(), e));

    }


    private <T extends Identifiable & Auditable> Mono<@NotNull T> withOldId(T newObject, Mono<T> oldObject) {
        return oldObject.map(o -> {
            newObject.setId(o.getId());
            return newObject;
        });
    }

    @NotNull
    private <R extends ReactiveMongoRepository<T, Long> & Sequenced, T extends Identifiable & Auditable> T withNewId(@NotNull T t, R r) {
        t.setId(sequenceService.generateSequence(r.getSequenceName()));
        return t;
    }

    private <R extends ReactiveMongoRepository<T, Long> & Sequenced, T extends Identifiable & Auditable> Mono<T> save(T t, @NotNull String username, Date date, R r) {
        AuditUtil.setAuditData(t, username, date);
        return r.save(t).flatMap(s -> r.findById(s.getId()));
    }

}
