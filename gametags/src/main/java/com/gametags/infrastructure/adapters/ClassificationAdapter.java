package com.gametags.infrastructure.adapters;

import com.gametags.domain.Classification;
import com.gametags.infrastructure.ClassificationDAO;
import com.gametags.infrastructure.mappers.ClassificationMapper;
import com.gametags.infrastructure.repositories.ClassificationRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        return repo.findAll().stream().map(entity -> mapper.fromEntityToDomain(entity)).collect(Collectors.toList());
    }

    public Classification update(Classification classification) {
        return mapper.fromEntityToDomain(repo.save(mapper.toEntity(classification)));
    }

    public Classification delete(UUID id) {
        Classification dao = this.findById(id);
        repo.deleteById(id);
        return dao;
    }
}
