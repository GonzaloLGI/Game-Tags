package com.gametags.gametags.application.videogame.add_new_classification;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

import com.gametags.gametags.domain.model.Classification;
import com.gametags.gametags.domain.model.VideoGame;
import com.gametags.gametags.domain.services.ClassificationService;
import com.gametags.gametags.domain.services.VideoGameService;
import com.gametags.gametags.infrastructure.dtos.ClassificationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AddNewClassificationUseCase {

  @Autowired
  private VideoGameService videoGameService;

  @Autowired
  private ClassificationService classificationService;

  public VideoGame addClassification(Classification newClassification, String videoGameName) {
    System.out.println("[START] addClassificationToVideoGame");
    log.debug("[START] addClassificationToVideoGame");
    VideoGame videoGameToUpdate = videoGameService.findVideoGameByName(videoGameName);
    if (Objects.isNull(videoGameToUpdate.getId())) {
      System.out.println("[STOP] addClassificationToVideoGame");
      log.debug("[STOP] addClassificationToVideoGame");
      throw new NoSuchElementException("The videogame is not registered");
    }
    System.out.println("[STOP] addClassificationToVideoGame");
    log.debug("[STOP] addClassificationToVideoGame");
    Classification createdClassification = checkAndAddNewClassifications(newClassification);
    if(!videoGameToUpdate.getClassifications().contains(createdClassification)){
      videoGameToUpdate.getClassifications().add(createdClassification);
    }
    return videoGameService.updateVideoGame(videoGameToUpdate);
  }

  private Classification checkAndAddNewClassifications(Classification classification){
    log.debug("CLASIFICACION: " + classification.toString());
    System.out.println("CLASIFICACION: " + classification);
    Classification existingClassification = classificationService.findOneClassificationBySystemAndTag(classification.getSystem(), classification.getTag());
    if(Objects.isNull(existingClassification.getId())){
      return classificationService.createClassification(classification);
    }
    return existingClassification;
  }
}
