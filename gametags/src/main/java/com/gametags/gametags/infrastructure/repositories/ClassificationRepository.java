package com.gametags.gametags.infrastructure.repositories;

import java.util.Optional;
import java.util.UUID;

import com.gametags.gametags.infrastructure.daos.ClassificationDAO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClassificationRepository extends MongoRepository<ClassificationDAO, UUID> {

  Optional<ClassificationDAO> findBySystemAndTag(String system, String tag);
}
