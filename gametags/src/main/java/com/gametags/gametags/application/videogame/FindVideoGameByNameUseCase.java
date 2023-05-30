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
public class FindVideoGameByNameUseCase {

  @Autowired
  private VideoGameService service;

  public VideoGame findByName(String name) {
    System.out.println("[START] findVideoGameByName");
    VideoGame result = service.findVideoGameByName(name);
    if (Objects.isNull(result.getId())) {
      System.out.println("[STOP] findVideoGameByName");
      throw new NoSuchElementException("The videogame is not registered");
    }
    System.out.println("[STOP] findVideoGameByName");
    return result;
  }
}
