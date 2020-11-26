package com.purini.service;

import com.purini.model.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceService {

    @Autowired
    private MongoOperations mongoOperations;

    public long generateSequence(String seqId) {
        Sequence sequence = mongoOperations.findAndModify(query(where("_id").is(seqId)),
                new Update().inc("seq", 1), options().returnNew(true).upsert(true), Sequence.class);
        return !Objects.isNull(sequence) ? sequence.getSeq() : 1;
    }
}
