package com.teste.webfluxjwt.controller;

import com.teste.webfluxjwt.model.Response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ResourceREST {

    @RequestMapping(value = "resource/user", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public Mono<ResponseEntity<?>> user() {
        return Mono.just(ResponseEntity.ok(new MessageResponse("Content for user")));
    }

    @RequestMapping(value = "resource/admin", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<?>> admin() {
        return Mono.just(ResponseEntity.ok(new MessageResponse("Content for admin")));
    }

    @RequestMapping(value = "resource/user-or-admin", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Mono<ResponseEntity<?>> userOrAdmin() {
        return Mono.just(ResponseEntity.ok(new MessageResponse("Content for user or admin")));
    }
}
