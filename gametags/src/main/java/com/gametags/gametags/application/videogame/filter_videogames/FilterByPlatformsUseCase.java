package com.gametags.gametags.application.videogame.filter_videogames;

import java.util.Arrays;
import java.util.List;

import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.VideoGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FilterByPlatformsUseCase {

  @Autowired
  private VideoGameService service;

  public List<VideoGame> videoGamesByPlatforms(List<String> platforms) {
    System.out.println("[START] filterByPlatforms");
    log.debug("[START] filterByPlatforms");
    System.out.println("[STOP] filterByPlatforms");
    log.debug("[STOP] filterByPlatforms");
    return service.findAllVideoGamesByPlatforms(platforms);
  }
}
