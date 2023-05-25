package com.gametags.gametags.application.classification;

import java.util.List;

import com.gametags.gametags.domain.model.Classification;
import com.gametags.gametags.domain.services.ClassificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FindAllClassificationsUseCase {

  @Autowired
  private ClassificationService service;

  public List<Classification> findAllClassifications() {
    System.out.println("[START] Searching all classifications");
    //    log.debug("[START] Searching all classifications");
    return service.findAllClassifications();
  }
}
