package com.polytechcloud.polytechcloud.controller;

import com.polytechcloud.polytechcloud.entity.User;
import com.polytechcloud.polytechcloud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    
    @GetMapping(path="/user")
    public ResponseEntity<?> getAllUsers(@RequestParam(value = "page", required = false) Integer page) {
        page = page == null ? 0 : page;
        List<User> users = userRepository.findAll(new Sort(Sort.Direction.ASC, "id"))
                .stream()
                .skip(100*page)
                .limit(100)
                .collect(Collectors.toList());

        return users.isEmpty()
                ? ResponseEntity.noContent().build()
                : new ResponseEntity<>(users, HttpStatus.OK);
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
