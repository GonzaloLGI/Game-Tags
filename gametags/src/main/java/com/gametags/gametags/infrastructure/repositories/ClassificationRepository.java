package com.gametags.gametags.infrastructure.repositories;

import com.gametags.gametags.infrastructure.ClassificationDAO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface ClassificationRepository extends MongoRepository<ClassificationDAO, UUID> {
}
