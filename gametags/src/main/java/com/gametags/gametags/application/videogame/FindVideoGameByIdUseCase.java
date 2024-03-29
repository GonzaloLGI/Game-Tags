package com.gametags.gametags.application.videogame;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.VideoGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FindVideoGameByIdUseCase {

  @Autowired
  private VideoGameService service;

  public VideoGame findOneVideoGame(UUID id) {
    log.info("[START] findOneVideoGame");
    VideoGame result = service.findOneVideoGame(id);
    if (Objects.isNull(result.getId())) {
      log.info("[STOP] findOneVideoGame");
      throw new NoSuchElementException("The videogame is not registered");
    }
    log.info("[STOP] findOneVideoGame");
    return result;
  }
}
