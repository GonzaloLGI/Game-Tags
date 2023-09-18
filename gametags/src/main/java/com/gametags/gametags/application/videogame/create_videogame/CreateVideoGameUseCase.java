package com.gametags.gametags.application.videogame.create_videogame;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import com.gametags.gametags.domain.model.Classification;
import com.gametags.gametags.domain.model.User;
import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.ClassificationService;
import com.gametags.gametags.domain.services.VideoGameService;
import com.gametags.gametags.infrastructure.dtos.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CreateVideoGameUseCase {

  @Autowired
  private VideoGameService service;

  @Autowired
  private ClassificationService classificationService;

  public VideoGame createVideoGame(VideoGame videoGame) {
    System.out.println("[START] createVideoGame");
    log.debug("[START] createVideoGame");
    VideoGame previous = service.findVideoGameByName(videoGame.getName());
    if (!Objects.isNull(previous.getId())) {
      System.out.println("[STOP] createVideoGame");
      log.debug("[STOP] createVideoGame");
      return previous;
    }
    List<Classification> updatedClassifications = videoGame.getClassifications().stream().map(classification -> checkAndAddNewClassifications(classification)).collect(
        Collectors.toList());
    videoGame.getClassifications().removeAll(videoGame.getClassifications());
    videoGame.getClassifications().addAll(updatedClassifications);
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String id = auth.getName();
    log.info("Created videogame by: "+id);
    System.out.println("[STOP] createVideoGame");
    log.info("[STOP] createVideoGame");
    return service.createVideoGame(videoGame);
  }

  private Classification checkAndAddNewClassifications(Classification classification){
    log.debug("CLASIFICACION: " + classification.getId());
    System.out.println("CLASIFICACION: " + classification.getId());
    Classification existingClassification = classificationService.findOneClassificationBySystemAndTag(classification.getSystem(), classification.getTag());
    if(Objects.isNull(existingClassification.getId())){
      Classification newClassification = Classification.builder()
          .id(UUID.randomUUID())
          .url(classification.getUrl())
          .tag(classification.getTag())
          .country(classification.getCountry())
          .system(classification.getSystem())
          .build();
      return classificationService.createClassification(newClassification);
    }
    return existingClassification;
  }
}
