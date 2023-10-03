package com.gametags.gametags.infrastructure.adapters;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.gametags.gametags.domain.model.Comment;
import com.gametags.gametags.infrastructure.daos.CommentDAO;
import com.gametags.gametags.infrastructure.mappers.CommentMapper;
import com.gametags.gametags.infrastructure.repositories.CommentRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Data
@Builder
@Repository
@AllArgsConstructor
@Slf4j
public class CommentAdapter {

  private CommentRepository repo;

  private CommentMapper mapper;

  public Comment update(Comment comment) {
    return mapper.fromEntityToDomain(repo.save(mapper.toEntity(comment)));
  }

  public List<Comment> findAll() {
    return repo.findAll().stream().map(entity -> mapper.fromEntityToDomain(entity)).collect(Collectors.toList());
  }

  public Comment findById(UUID id) {
    return mapper.fromEntityToDomain(repo.findById(id).orElseGet(() -> CommentDAO.builder().build()));
  }

  public Comment delete(UUID id) {
    Comment dao = this.findById(id);
    repo.deleteById(id);
    return dao;
  }

  public Comment create(Comment comment) {
    return mapper.fromEntityToDomain(repo.save(mapper.toEntity(comment)));
  }

  public List<Comment> findAllByCategoryAndUploadUser(String category, UUID userId) {
    return repo.findAllByCategoryAndUploadUser(category, userId).stream().map(entity -> mapper.fromEntityToDomain(entity)).collect(Collectors.toList());
  }

  public List<Comment> findAllBySeverityAndUploadUser(String severity, UUID userId) {
    return repo.findAllBySeverityAndUploadUser(severity, userId).stream().map(entity -> mapper.fromEntityToDomain(entity)).collect(Collectors.toList());
  }

  public List<Comment> findAllByVideoGameAndUploadUser(UUID videogame, UUID userId) {
    return repo.findAllByVideogameAndUploadUser(videogame, userId).stream().map(entity -> mapper.fromEntityToDomain(entity)).collect(Collectors.toList());
  }

  public List<Comment> findAllByCategoryAndVideoGame(String category, UUID videogame) {
    return repo.findAllByCategoryAndVideogame(category, videogame).stream().map(entity -> mapper.fromEntityToDomain(entity)).collect(Collectors.toList());
  }

  public List<Comment> findAllBySeverityAndVideoGame(String severity, UUID videogame) {
    return repo.findAllBySeverityAndVideogame(severity, videogame).stream().map(entity -> mapper.fromEntityToDomain(entity)).collect(Collectors.toList());
  }

  public List<Comment> findAllOfUser(UUID userid) {
    return repo.findAllByUploadUser(userid).stream().map(entity -> mapper.fromEntityToDomain(entity)).collect(Collectors.toList());
  }
}
