package com.gametags.gametags.application.videogame.filter_videogames;

import java.util.List;

import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.VideoGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FilterByDeveloperUseCase {

  @Autowired
  private VideoGameService service;

  public List<VideoGame> videoGamesByDeveloper(String developer) {
    System.out.println("[START] filterByDeveloper");
    log.debug("[START] filterByDeveloper");
    System.out.println("[STOP] filterByDeveloper");
    log.debug("[STOP] filterByDeveloper");
    return service.findAllVideoGamesByDeveloper(developer);
  }
}
