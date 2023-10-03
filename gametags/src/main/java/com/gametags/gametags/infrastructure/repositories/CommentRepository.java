package com.gametags.gametags.infrastructure.repositories;

import java.util.List;
import java.util.UUID;

import com.gametags.gametags.infrastructure.daos.CommentDAO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<CommentDAO, UUID> {

  List<CommentDAO> findAllByCategoryAndUploadUser(String category, UUID uploadUser);

  List<CommentDAO> findAllBySeverityAndUploadUser(String severity, UUID uploadUser);

  List<CommentDAO> findAllByVideogameAndUploadUser(UUID videogame, UUID uploadUser);

  List<CommentDAO> findAllByCategoryAndVideogame(String category, UUID videogame);

  List<CommentDAO> findAllBySeverityAndVideogame(String severity, UUID videogame);

  List<CommentDAO> findAllByUploadUser(UUID userId);
}
