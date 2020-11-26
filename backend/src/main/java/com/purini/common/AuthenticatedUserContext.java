package com.purini.common;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class AuthenticatedUserContext {

    public static <R> Flux<R> flatMapMany(Function<UsernamePasswordAuthenticationToken, Flux<R>> userMapper) {
        return ReactiveSecurityContextHolder.getContext().map(SecurityContext::getAuthentication)
                .map(auth -> (UsernamePasswordAuthenticationToken) auth).single().flatMapMany(userMapper);
    }

    public static <R> Mono<R> flatMap(Function<UsernamePasswordAuthenticationToken, Mono<R>> userMapper) {
        return ReactiveSecurityContextHolder.getContext().map(SecurityContext::getAuthentication)
                .map(auth -> (UsernamePasswordAuthenticationToken) auth).single().flatMap(userMapper);
    }
}
