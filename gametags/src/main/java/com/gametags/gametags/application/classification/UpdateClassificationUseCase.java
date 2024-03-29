package com.gametags.gametags.application.classification;

import java.util.NoSuchElementException;
import java.util.Objects;

import com.gametags.gametags.domain.model.Classification;
import com.gametags.gametags.domain.services.ClassificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UpdateClassificationUseCase {

  @Autowired
  private ClassificationService service;

  public Classification updateClassification(Classification classification) {
    log.info("[START] updateClassification");
    Classification foundClassification = service.findOneClassification(classification.getId());
    if (Objects.isNull(foundClassification.getId())) {
      throw new NoSuchElementException("The classification is not registered");
    }
    log.info("[STOP] updateClassification");
    return service.updateClassification(classification);
  }
}
