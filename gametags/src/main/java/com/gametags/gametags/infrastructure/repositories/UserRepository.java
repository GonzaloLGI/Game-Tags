package com.gametags.gametags.infrastructure.repositories;

import com.gametags.gametags.infrastructure.daos.UserDAO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserDAO, String> {
}
