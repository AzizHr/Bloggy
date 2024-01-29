package com.example.bloggy.repository;

import com.example.bloggy.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    boolean existsUserByEmail(String email);
    Optional<User> findUserByEmail(String email);

}
