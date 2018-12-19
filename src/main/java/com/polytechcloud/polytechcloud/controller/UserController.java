package com.polytechcloud.polytechcloud.controller;

import com.polytechcloud.polytechcloud.entity.User;
import com.polytechcloud.polytechcloud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path="/user")
    public Iterable<User> getAllUsers(@RequestParam(value = "page", required = false) Integer page) {
        page = page == null ? 0 : page;
        return userRepository.findAll().stream()
                //.sorted(Comparator.comparing(User::getId))
                .skip(100*page)
                .limit(100)
                .collect(Collectors.toSet());
        //return userRepository.findAll();
    }

    @GetMapping(path = "user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id){
        Optional<User> user = userRepository.findById(id);

        return user.isPresent() ?
                new ResponseEntity<>(user, HttpStatus.OK)
                : ResponseEntity.notFound().build();
    }

    @PutMapping(path = "/user")
    public ResponseEntity<List<User>> addAllUsers(@RequestBody List<User> users) {
        userRepository.deleteAll();
        userRepository.saveAll(users);
        return new ResponseEntity<>(users, HttpStatus.CREATED);
    }

    @PutMapping(path = "/user/{id}")
    public ResponseEntity<Optional<User>> addAllUsers(@PathVariable String id, @RequestBody User newUser) {

        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()) {

            user.get().update(newUser);
            userRepository.save(user.get());
            return new ResponseEntity<>(user, HttpStatus.OK);

        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping(path = "/user")
    public ResponseEntity<User> addNewUser(@RequestBody User user) throws URISyntaxException {

        if (user == null)
            return ResponseEntity.noContent().build();

        userRepository.save(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/user")
    public void deleteAllUsers() {
        //Todo : code de retour
        userRepository.deleteAll();


    }

    @DeleteMapping(path = "/user/{id}")
    public ResponseEntity<Optional<User>> deleteUser(@PathVariable String id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(500).build();
    }

}
