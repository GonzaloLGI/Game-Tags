package com.gametags.gametags.application.videogame;

import java.util.NoSuchElementException;
import java.util.Objects;

import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.VideoGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UpdateVideoGameUseCase {

  @Autowired
  private VideoGameService service;

  public VideoGame updateVideoGame(VideoGame videogame) {
    System.out.println("[START] updateVideoGame");
    VideoGame toUpdate = service.findOneVideoGame(videogame.getId());
    if (Objects.isNull(toUpdate.getId())) {
      System.out.println("[STOP] updateVideoGame");
      throw new NoSuchElementException("The videogame is not registered");
    }
    System.out.println("[STOP] updatedVideoGame");
    return service.updateVideoGame(videogame);
  }
}
