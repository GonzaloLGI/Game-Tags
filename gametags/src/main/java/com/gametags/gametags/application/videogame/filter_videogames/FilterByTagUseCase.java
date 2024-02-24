package com.gametags.gametags.application.videogame.filter_videogames;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.VideoGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FilterByTagUseCase {

  @Autowired
  private VideoGameService service;

  public List<VideoGame> videoGamesByTag(String tag) {
    log.info("[START] filterByTag: " + tag);
    List<String> tagsToFind = new ArrayList<>();
    switch(tag){
      case "3":
        tagsToFind.addAll(Arrays.asList("3","E","G","U","0","A"));
        break;
      case "7":
        tagsToFind.addAll(Arrays.asList("7","E+10","PG","6","B"));
        break;
      case "12":
        tagsToFind.addAll(Arrays.asList("12","T","M","C"));
        break;
      case "16":
        tagsToFind.addAll(Arrays.asList("16","T","MA+15","15","D"));
        break;
      case "18":
        tagsToFind.addAll(Arrays.asList("18","M","R+18","Z"));
        break;
      default:
        log.info("Invalid age tag");
        break;
    }
    log.info("[STOP] filterByTag: " + tagsToFind.toString());
    return service.findAllVideoGamesByTag(tagsToFind);
  }
}
