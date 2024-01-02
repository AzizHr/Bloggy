package com.example.bloggy.repository;

import com.example.bloggy.model.CustomUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<CustomUser, String> {
    CustomUser findByEmail(String email);
}
