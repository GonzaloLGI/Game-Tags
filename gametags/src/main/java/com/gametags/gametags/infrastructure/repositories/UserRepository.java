package com.gametags.gametags.infrastructure.repositories;

import java.util.Optional;
import java.util.UUID;

import com.gametags.gametags.infrastructure.daos.UserDAO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserDAO, UUID> {

  Optional<UserDAO> findByUsername(String username);
}
