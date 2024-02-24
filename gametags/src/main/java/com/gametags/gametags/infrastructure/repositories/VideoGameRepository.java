package com.gametags.gametags.infrastructure.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.gametags.gametags.infrastructure.daos.VideoGameDAO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface VideoGameRepository extends MongoRepository<VideoGameDAO, UUID> {
  Optional<VideoGameDAO> findByName(String name);

  List<VideoGameDAO> findAllByDeveloper(String developer);

  List<VideoGameDAO> findAllByPlatformsIn(List<String> platforms);

  List<VideoGameDAO> findFirst3ByOrderByUploadDateTimeDesc();

  //@Query(value = "{ 'classifications.tag' : ?0 }")
  List<VideoGameDAO> findAllVideogamesByClassificationsTagIn(List<String> tag);

  @Query(value = "{ 'classifications.system' : ?0 }")
  List<VideoGameDAO> findVideogamesByClassificationsSystem(String system);
}
