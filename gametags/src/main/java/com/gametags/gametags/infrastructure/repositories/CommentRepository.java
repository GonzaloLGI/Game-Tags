package com.gametags.gametags.infrastructure.repositories;

import java.util.UUID;

import com.gametags.gametags.infrastructure.daos.CommentDAO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<CommentDAO, UUID> {

}
