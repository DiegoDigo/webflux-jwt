package com.teste.webfluxjwt.controller;

import com.teste.webfluxjwt.model.AuthRequest;
import com.teste.webfluxjwt.model.Response.AuthResponse;
import com.teste.webfluxjwt.security.PBKDF2Encoder;
import com.teste.webfluxjwt.service.UserService;
import com.teste.webfluxjwt.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "user")
public class AuthenticationREST {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private PBKDF2Encoder passwordEncoder;

    @Autowired
    private UserService userService;


    @PostMapping("login")
    public Mono<ResponseEntity<AuthResponse>> auth(@RequestBody AuthRequest ar) {
        return userService.findByUsername(ar.getUsername()).map((userDetails) -> {
            if (passwordEncoder.encode(ar.getPassword()).equals(userDetails.getPassword())) {
                return ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(userDetails)));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        });
    }
}
