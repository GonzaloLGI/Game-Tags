package com.gametags.gametags.application.videogame.create_videogame;

import java.util.Objects;

import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.VideoGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CreateVideoGameUseCase {

  @Autowired
  private VideoGameService service;

  public VideoGame createVideoGame(VideoGame videoGame) {
    System.out.println("[START] createVideoGame");
    log.debug("[START] createVideoGame");
    VideoGame previous = service.findVideoGameByName(videoGame.getName());
    if (!Objects.isNull(previous.getId())) {
      System.out.println("[STOP] createVideoGame");
      log.debug("[STOP] createVideoGame");
      return previous;
    }
    System.out.println("[STOP] createVideoGame");
    log.debug("[STOP] createVideoGame");
    return service.createVideoGame(videoGame);
  }
}
