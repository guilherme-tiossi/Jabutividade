package com.jabutividade.backEnd.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.jabutividade.backEnd.entities.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query("{$or:[{'username':?0}, {'email':?0}]}")
    Optional<User> findByUsernameOrEmail(String usernameOrEmail);
}
