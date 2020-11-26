package com.purini.api;

import com.purini.security.*;
import com.purini.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Api(value = "API for Login")
@RestController
public class AuthenticationController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private PBKDF2Encoder passwordEncoder;

    @Autowired
    private UserService userRepository;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest ar) {
        return userRepository.findByUsername(ar.getUsername()).map((userDetails) -> {
            if (passwordEncoder.encode(ar.getPassword()).equals(userDetails.getPassword())) {
                return ResponseEntity.ok(new AuthResponse(
                        userDetails.getUsername(),
                        userDetails.getRoles().stream().map(Role::name).collect(Collectors.toList()),
                        jwtUtil.generateToken(userDetails)));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

}