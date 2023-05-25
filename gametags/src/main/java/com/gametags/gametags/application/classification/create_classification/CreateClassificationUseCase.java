package com.gametags.gametags.application.classification.create_classification;

import java.util.Objects;

import com.gametags.gametags.domain.model.Classification;
import com.gametags.gametags.domain.services.ClassificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CreateClassificationUseCase {

  @Autowired
  private ClassificationService service;

  public Classification createClassification(Classification classification) {
    //ESTO NO TIENE MUCHO SENTIDO YA QUE PODRIA HABER VARIAS CLASIFICACIONES IGUALES PERO CON DISTINTO ID
    //SE DEBERIA COMPROBAR CONSULTANDO SI YA EXISTE UNA CLASIFICACION DE LA MISMA ENTIDAD EN EL VIDEOJUEGO

    System.out.println(
        "[START] Creating classification with data: " + classification.getId() + classification.getSystem() + classification.getCountry());
    //    log.debug(
    //        "[START] Creating classification with data: " + classification.getId() + classification.getSystem() + classification
    //        .getCountry());
    Classification previous = service.findOneClassification(classification.getId());
    if (Objects.isNull(previous.getId())) {
      return service.createClassification(classification);
    }
    System.out.println("[STOP] createClassification");
    //    log.debug("[STOP] createClassification");
    return previous;
  }
}
