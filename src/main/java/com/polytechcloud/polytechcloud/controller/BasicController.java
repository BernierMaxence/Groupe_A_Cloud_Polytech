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

    @GetMapping(path="/users")
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path = "user/{id}")
    public User getUserById(@PathVariable int id){
        return userRepository.findById(id);

    }


    @PutMapping(path = "user/")
    public User updateUser(@RequestBody User user){
        return userRepository.save(user);
    }

    @PutMapping(path = "/users")
    public User addAllUsers() {
        //Todo : code de retour
        //Todo : "permet de remplacer la collection entière par une nouvelle liste d'utilisateur"
        User user = new User(1, "aaa", "bbb", Date.from(Instant.now()), 100, 100);
        return userRepository.save(user);
    }

    @PostMapping(path = "/user")
    public ResponseEntity<?> addNewUser(@RequestBody User user) throws URISyntaxException {
        if (user == null)
            return ResponseEntity.noContent().build();

        User userAdded = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }





    @DeleteMapping(path = "/users")
    public void deleteAllUsers() {
        //Todo : code de retour
        User user = new User(1, "aaa", "bbb", Date.from(Instant.now()), 100, 100);
        userRepository.deleteAll();


    }

    @DeleteMapping(path = "/user/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);

    }

}
