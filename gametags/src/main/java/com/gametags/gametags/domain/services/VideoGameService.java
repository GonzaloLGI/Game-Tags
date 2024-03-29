package com.gametags.gametags.domain.services;

import java.util.List;
import java.util.UUID;

import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.infrastructure.adapters.VideoGameAdapter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
@Builder
public class VideoGameService {

  private VideoGameAdapter adapter;

  public VideoGame createVideoGame(VideoGame videoGame) {
    return adapter.create(videoGame);
  }

  public VideoGame findOneVideoGame(UUID id) {
    return adapter.findById(id);
  }

  public List<VideoGame> findAllVideoGames() {
    return adapter.findAll();
  }

  public VideoGame updateVideoGame(VideoGame videoGame) {
    return adapter.update(videoGame);
  }

  public VideoGame deleteVideoGame(UUID id) {
    return adapter.delete(id);
  }

  public List<VideoGame> findVideoGameLikeName(String name){
    return adapter.findLikeName(name);
  }

  public VideoGame findVideoGameByName(String name){
    return adapter.findByName(name);
  }

  public List<VideoGame> findAllVideoGamesByDeveloper(String developer) {
    return adapter.findAllByDeveloper(developer);
  }

  public List<VideoGame> findAllVideoGamesByPlatforms(List<String> platforms) {
    List<VideoGame> result = adapter.findAllByPlatforms(platforms);
    log.debug(result.toString());
    return result;
  }

  public List<VideoGame> findAllVideoGamesByTag(List<String> tag) {
    return adapter.findAllByTag(tag);
  }

  public List<VideoGame> findAllVideoGamesBySystem(String system) {
    return adapter.findAllBySystem(system);
  }

  public List<VideoGame> findThreeLatestVideogames() {
    return adapter.findThreeLatestVideogames();
  }

  public List<VideoGame> findAllVideoGamesByUser(String username){
    return adapter.findAllVideoGamesByUser(username);
  }
}
