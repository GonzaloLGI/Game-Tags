package com.gametags.gametags.domain.services;

import java.util.List;
import java.util.UUID;

import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.infrastructure.adapters.CommentAdapter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
@Builder
public class CommentService {

  private CommentAdapter adapter;

  public Comment updateComment(Comment comment) {
    return adapter.update(comment);
  }

  public List<Comment> findAllComments() {
    return adapter.findAll();
  }

  public Comment findOneCommentById(UUID id) {
    return adapter.findById(id);
  }

  public Comment deleteComment(UUID id) {
    return adapter.delete(id);
  }

  public Comment createComment(Comment comment) {
    return adapter.create(comment);
  }

  public List<Comment> findAllCommentsByCategoryAndUploadUser(String category, UUID userId) {
    return adapter.findAllByCategoryAndUploadUser(category, userId);
  }

  public List<Comment> findAllCommentsBySeverityAndUploadUser(String severity, UUID userId) {
    return adapter.findAllBySeverityAndUploadUser(severity, userId);
  }

  public List<Comment> findAllCommentsByVideoGameAndUploadUser(UUID videogame, UUID userId) {
    return adapter.findAllByVideoGameAndUploadUser(videogame, userId);
  }

  public List<Comment> findAllCommentsByCategoryAndVideoGame(String category, UUID videogame) {
    return adapter.findAllByCategoryAndVideoGame(category, videogame);
  }

  public List<Comment> findAllCommentsBySeverityAndVideoGame(String severity, UUID videogame) {
    return adapter.findAllBySeverityAndVideoGame(severity, videogame);
  }
}
