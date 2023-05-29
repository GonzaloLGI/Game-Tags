package com.gametags.gametags.infrastructure.repositories;

import java.util.UUID;

import com.gametags.gametags.infrastructure.daos.VideoGameDAO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoGameRepository extends MongoRepository<VideoGameDAO, UUID> {

}
