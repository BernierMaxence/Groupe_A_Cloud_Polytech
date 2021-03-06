package com.polytechcloud.polytechcloud.controller;

import com.polytechcloud.polytechcloud.entity.User;
import com.polytechcloud.polytechcloud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /* Get Mapping */

    @GetMapping(path="/user")
    public ResponseEntity<List<User>> getAllUsers(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "gt", required = false) Integer gt) {
        page = page == null ? 0 : page;

        Stream<User> userStream = userRepository.findAll(new Sort(Sort.Direction.ASC, "id")).stream();
        if(gt != null) {

            Date now = new Date();
            userStream = userStream.filter(user -> (now.getTime()-user.getBirthDay().getTime())/1000/60/60/24/365 > gt);
        }

        userStream = userStream.skip(100L*page).limit(100);

        List<User> users = userStream.collect(Collectors.toList());

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(path = "user/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable String id){
        Optional<User> user = userRepository.findById(id);

        return user.isPresent() ?
                new ResponseEntity<>(user, HttpStatus.OK)
                : ResponseEntity.notFound().build();
    }

    /* Put Mapping */

    @PutMapping(path = "/user")
    public ResponseEntity<List<User>> addAllUsers(@RequestBody List<User> users) {
        if (users.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            userRepository.deleteAll();
            userRepository.saveAll(users);
            return new ResponseEntity<>(users, HttpStatus.CREATED);
        }
    }

    @PutMapping(path = "/user/{id}")
    public ResponseEntity<Optional<User>> addOneUser(@PathVariable String id, @RequestBody User newUser) {

        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {

            user.get().update(newUser);
            userRepository.save(user.get());
            return new ResponseEntity<>(user, HttpStatus.OK);

        }

        return ResponseEntity.notFound().build();

    }

    /* Post Mapping */

    @PostMapping(path = "/user")
    public ResponseEntity<User> addNewUser(@RequestBody User user) {
        userRepository.save(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /* Delete Mapping */

    @DeleteMapping(path = "/user")
    public ResponseEntity deleteAllUsers() {
        userRepository.deleteAll();
        return ResponseEntity.ok().build();
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
