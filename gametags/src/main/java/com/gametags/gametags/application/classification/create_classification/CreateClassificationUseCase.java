package com.gametags.gametags.application.classification.create_classification;

import java.util.Objects;

import com.gametags.gametags.domain.model.Classification;
import com.gametags.gametags.domain.services.ClassificationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.AlreadyBuiltException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CreateClassificationUseCase {

  @Autowired
  private ClassificationService service;

  public Classification createClassification(Classification classification) {
    log.info("[START] Creating classification with data: " + classification.getId() + " " + classification.getSystem() + " " + classification.getCountry());
    if (Objects.isNull(service.findOneClassification(classification.getId()).getId()) &&
            Objects.isNull(service.findOneClassificationBySystemAndTag(classification.getSystem(), classification.getTag()).getId())) {
      log.info("[STOP] createClassification");
      return service.createClassification(classification);
    }else{
      throw new AlreadyBuiltException("Classification already exists");
    }
  }
}
