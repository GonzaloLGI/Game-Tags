package com.gametags.gametags.application.videogame.filter_videogames;

import java.util.List;

import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.VideoGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FilterBySystemUseCase {

  @Autowired
  private VideoGameService service;

  public List<VideoGame> videoGamesBySystem(String system) {
    log.info("[START] filterBySystem: " + system);
    log.info("[STOP] filterBySystem: " + system);
    return service.findAllVideoGamesBySystem(system);
  }
}
