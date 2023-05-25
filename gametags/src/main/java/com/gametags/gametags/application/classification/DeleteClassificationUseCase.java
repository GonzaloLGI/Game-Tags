package com.gametags.gametags.application.classification;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

import com.gametags.gametags.domain.model.Classification;
import com.gametags.gametags.domain.services.ClassificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeleteClassificationUseCase {

  @Autowired
  private ClassificationService service;

  public Classification deleteClassification(UUID id) {
    //    log.debug("[START] Deleting classification with id: " + id);
    System.out.println("[START] Deleting classification with id: " + id);
    Classification classification = service.findOneClassification(id);
    if (!Objects.isNull(classification.getId())) {
      System.out.println("[STOP] deleteClassification");
      //      log.debug("[STOP] deleteClassification");
      return service.deleteClassification(id);
    } else {
      throw new NoSuchElementException("Non existing classification");
    }

  }
}
