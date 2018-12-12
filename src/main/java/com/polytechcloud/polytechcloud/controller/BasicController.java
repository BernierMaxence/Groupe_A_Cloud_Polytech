package com.polytechcloud.polytechcloud.controller;

import com.polytechcloud.polytechcloud.entity.User;
import com.polytechcloud.polytechcloud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Date;

@RestController
public class BasicController {

    private final UserRepository userRepository;

    @Autowired
    public BasicController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* @GetMapping(path="/")
    public String get() {
        return "Hello world";
    }*/

}
