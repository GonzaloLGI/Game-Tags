package com.gametags.gametags.application.videogame;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.VideoGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FindVideoGameByNameUseCase {

  @Autowired
  private VideoGameService service;

  public List<VideoGame> findByName(String name) {
    log.info("[START] findVideoGameByName");
    List<VideoGame> result = service.findVideoGameLikeName(name);
    if (result.isEmpty()) {
      log.info("[STOP] findVideoGameByName");
      throw new NoSuchElementException("The videogame is not registered");
    }
    log.info("[STOP] findVideoGameByName");
    return result;
  }
}
