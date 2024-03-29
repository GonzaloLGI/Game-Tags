package com.gametags.gametags.application.videogame;

import java.util.List;

import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.VideoGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FindAllVideoGamesUseCase {

  @Autowired
  private VideoGameService service;

  public List<VideoGame> findAllVideoGames() {
    log.info("[START] findAllVideoGames");
    log.info("[STOP] findAllVideoGames");
    return service.findAllVideoGames();
  }
}
