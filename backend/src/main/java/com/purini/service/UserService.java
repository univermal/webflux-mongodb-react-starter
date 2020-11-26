package com.purini.service;

import com.purini.model.User;
import com.purini.security.Role;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
public class UserService {

    /* TODO: Remove hardcoding, load from database - start */

    //username:password -> user:user
    private final String userUsername = "user";// password: user
    private final User user = new User(1L, userUsername, "cBrlgyL2GI2GINuLUUwgojITuIufFycpLG4490dhGtY=", true, Collections.singletonList(Role.ROLE_SAMPLE), null);

    public Mono<User> findByUsername(String username) {
        switch (username) {
            case userUsername:
                return Mono.just(user);
            default:
                return Mono.empty();
        }
    }
    /* Remove hardcoding - end */

}