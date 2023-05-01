package com.gametags.gametags.infrastructure.repositories;

import com.gametags.gametags.infrastructure.daos.UserDAO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends MongoRepository<UserDAO, UUID> {
    Optional<UserDAO> findByUsername(String username);
}
