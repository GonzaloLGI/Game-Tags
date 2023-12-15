package com.gametags.gametags.infrastructure.repositories;

import java.util.List;
import java.util.UUID;

import com.gametags.gametags.infrastructure.daos.CommentDAO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<CommentDAO, UUID> {

  List<CommentDAO> findAllByCategoryAndUploadUser(String category, String uploadUser);

  List<CommentDAO> findAllBySeverityAndUploadUser(String severity, String uploadUser);

  List<CommentDAO> findAllByVideogameAndUploadUser(String videogame, String uploadUser);

  List<CommentDAO> findAllByCategoryAndVideogame(String category, String videogame);

  List<CommentDAO> findAllBySeverityAndVideogame(String severity, String videogame);

  List<CommentDAO> findAllByUploadUser(String userName);
}
