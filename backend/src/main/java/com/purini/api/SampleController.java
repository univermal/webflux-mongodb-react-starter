package com.purini.api;

import com.purini.common.AuthenticatedUserContext;
import com.purini.common.UserAccessLogger;
import com.purini.config.ApplicationConfig;
import com.purini.model.Sample;
import com.purini.service.SampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/sample")
public class SampleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleController.class);

    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private SampleService sampleService;

    @PreAuthorize("hasRole('ROLE_SAMPLE')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Sample> get(@PathVariable Long id) {
        LOGGER.debug("inside sample get for id {}", id);
        return AuthenticatedUserContext.flatMap(user -> {
            UserAccessLogger.log(user);
            return sampleService.getById(id);
        });
    }

    @PreAuthorize("hasRole('ROLE_SAMPLE')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Sample> getAll() {
        return AuthenticatedUserContext.flatMapMany(user -> {
            UserAccessLogger.log(user);
            return sampleService.getAll();
        });
    }

    @PreAuthorize("hasRole('ROLE_SAMPLE')")
    @PostMapping(value = "/upsert")
    public Mono<Sample> upsert(@RequestBody Sample sample) {
        return AuthenticatedUserContext.flatMap(user -> {
            UserAccessLogger.log(user);
            return sampleService.upsert(sample, user.getName());
        });
    }
}
