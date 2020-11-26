package com.purini.repository;

import com.purini.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, Long>, Sequenced {

    @Override
    default String getSequenceName() {
        return "user_seq";
    }
}
