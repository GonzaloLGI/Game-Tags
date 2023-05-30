package com.gametags.gametags.infrastructure.adapters;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.infrastructure.daos.VideoGameDAO;
import com.gametags.gametags.infrastructure.mappers.VideoGameMapper;
import com.gametags.gametags.infrastructure.repositories.VideoGameRepository;
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
public class VideoGameAdapter {

  private VideoGameMapper mapper;

  private VideoGameRepository repo;

  public VideoGame update(VideoGame videogame) {
    return mapper.fromEntityToDomain(repo.save(mapper.toEntity(videogame)));
  }

  public List<VideoGame> findAll() {
    return repo.findAll().stream().map(entity -> mapper.fromEntityToDomain(entity)).collect(Collectors.toList());
  }

  public VideoGame findById(UUID id) {
    return mapper.fromEntityToDomain(repo.findById(id).orElseGet(() -> VideoGameDAO.builder().build()));
  }

  public VideoGame delete(UUID id) {
    VideoGame dao = this.findById(id);
    repo.deleteById(id);
    return dao;
  }

  public VideoGame create(VideoGame videogame) {
    return mapper.fromEntityToDomain(repo.save(mapper.toEntity(videogame)));
  }

  public VideoGame findByName(String name) {
    return mapper.fromEntityToDomain(repo.findByName(name).orElseGet(() -> VideoGameDAO.builder().build()));
  }
}
