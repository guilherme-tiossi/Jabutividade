package com.jabutividade.backEnd.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jabutividade.backEnd.entities.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}
