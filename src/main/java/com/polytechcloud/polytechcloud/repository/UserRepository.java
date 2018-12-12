package com.polytechcloud.polytechcloud.repository;

import com.polytechcloud.polytechcloud.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    public Optional<User> findById(String id);
    public void deleteById(String id);
}
