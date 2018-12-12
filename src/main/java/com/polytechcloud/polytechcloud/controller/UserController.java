package com.polytechcloud.polytechcloud.controller;

import com.polytechcloud.polytechcloud.entity.User;
import com.polytechcloud.polytechcloud.repository.UserRepository;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.OrderComparator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* @GetMapping(path="/")
    public String get() {
        return "Hello world";
    }*/

    @GetMapping(path="/user")
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path = "user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id){
        Optional<User> user = userRepository.findById(id);

        return user.isPresent() ?
                new ResponseEntity<>(user, HttpStatus.OK)
                : ResponseEntity.notFound().build();
    }

    @PutMapping(path = "/user")
    public ResponseEntity<?> addAllUsers(@RequestBody List<User> users) {
        userRepository.deleteAll();
        userRepository.saveAll(users);
        return new ResponseEntity<>(users, HttpStatus.CREATED);
    }

    @PutMapping(path = "/user/{id}")
    public ResponseEntity<?> addAllUsers(@PathVariable String id, @RequestBody User newUser) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            user.get().update(newUser);
            userRepository.save(user.get());
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(path = "/user")
    public ResponseEntity<?> addNewUser(@RequestBody User user) throws URISyntaxException {
        if (user == null)
            return ResponseEntity.noContent().build();

        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/user")
    public void deleteAllUsers() {
        //Todo : code de retour
        //User user = new User(1, "aaa", "bbb", Date.from(Instant.now()), 100, 100);
        userRepository.deleteAll();


    }

    @DeleteMapping(path = "/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(500).build();
    }

}
