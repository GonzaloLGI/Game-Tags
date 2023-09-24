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
    log.info("[START] addClassificationToVideoGame: " + videoGameName);
    VideoGame videoGameToUpdate = videoGameService.findVideoGameByName(videoGameName);
    if (Objects.isNull(videoGameToUpdate.getId())) {
      log.info("[STOP] addClassificationToVideoGame: " + videoGameName);
      throw new NoSuchElementException("The videogame is not registered");
    }
    log.info("[STOP] addClassificationToVideoGame: " + videoGameName);
    Classification createdClassification = checkAndAddNewClassifications(newClassification);
    if(!videoGameToUpdate.getClassifications().contains(createdClassification)){
      videoGameToUpdate.getClassifications().add(createdClassification);
    }
    return videoGameService.updateVideoGame(videoGameToUpdate);
  }

  private Classification checkAndAddNewClassifications(Classification classification){
    log.info("CLASIFICACION: " + classification.toString());
    Classification existingClassification = classificationService.findOneClassificationBySystemAndTag(classification.getSystem(), classification.getTag());
    if(Objects.isNull(existingClassification.getId())){
      return classificationService.createClassification(classification);
    }
    return existingClassification;
  }
}
