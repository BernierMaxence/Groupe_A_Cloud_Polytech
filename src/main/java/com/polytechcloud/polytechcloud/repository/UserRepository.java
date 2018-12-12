package com.polytechcloud.polytechcloud.repository;

import com.polytechcloud.polytechcloud.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    public User findById(int id);
    public User deleteById(int id);
}
