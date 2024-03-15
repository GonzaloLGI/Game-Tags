package com.gametags.gametags.infrastructure.adapters;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.gametags.gametags.domain.model.Classification;
import com.gametags.gametags.infrastructure.daos.ClassificationDAO;
import com.gametags.gametags.infrastructure.mappers.ClassificationMapper;
import com.gametags.gametags.infrastructure.repositories.ClassificationRepository;
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
public class ClassificationAdapter {

  private ClassificationRepository repo;

  private ClassificationMapper mapper;

  public Classification create(Classification classification) {
    return mapper.fromEntityToDomain(repo.save(mapper.toEntity(classification)));
  }

  public Classification findById(UUID id) {
    return mapper.fromEntityToDomain(repo.findById(id).orElseGet(() -> ClassificationDAO.builder().build()));
  }

  public List<Classification> findAll() {
    return repo.findAll().stream().map(entity -> mapper.fromEntityToDomain(entity)).toList();
  }

  public Classification update(Classification classification) {
    return mapper.fromEntityToDomain(repo.save(mapper.toEntity(classification)));
  }

  public Classification delete(UUID id) {
    Classification dao = this.findById(id);
    repo.deleteById(id);
    return dao;
  }

  public Classification findBySystemAndTag(String system, String tag) {
    return mapper.fromEntityToDomain(repo.findBySystemAndTag(system,tag).orElseGet(() -> ClassificationDAO.builder().build()));
  }
}
