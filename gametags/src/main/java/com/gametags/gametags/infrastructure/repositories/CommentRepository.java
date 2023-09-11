package com.gametags.gametags.infrastructure.repositories;

import java.util.List;
import java.util.UUID;

import com.gametags.gametags.infrastructure.daos.CommentDAO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<CommentDAO, UUID> {

  List<CommentDAO> findAllByCategory(String category);

  List<CommentDAO> findAllBySeverity(String severity);

  List<CommentDAO> findAllByVideogame(UUID videogame);

  List<CommentDAO> findAllByCategoryAndVideogame(String category, UUID videogame);

  List<CommentDAO> findAllBySeverityAndVideogame(String severity, UUID videogame);
}
