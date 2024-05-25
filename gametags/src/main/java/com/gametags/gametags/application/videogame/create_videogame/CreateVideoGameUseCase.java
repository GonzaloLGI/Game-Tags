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
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
public class CreateVideoGameUseCase {

  @Autowired
  private VideoGameService service;

  @Autowired
  private ClassificationService classificationService;

  public VideoGame createVideoGame(VideoGame videoGame) {
    log.info("[START] createVideoGame");
    log.info(String.valueOf(videoGame.getClassifications().get(0).getId()));
    VideoGame previous = service.findVideoGameByName(videoGame.getName());
    if (!Objects.isNull(previous.getId())) {
      log.info("[STOP] createVideoGame");
      return previous;
    }
    List<Classification> updatedClassifications = videoGame.getClassifications().stream().map(this::checkAndAddNewClassifications).toList();
    videoGame.getClassifications().removeAll(videoGame.getClassifications());
    videoGame.getClassifications().addAll(updatedClassifications);
    log.info("[STOP] createVideoGame");
    return service.createVideoGame(videoGame);
  }

  private Classification checkAndAddNewClassifications(Classification classification){
    log.info("CLASIFICACION: " + classification.getId());
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
