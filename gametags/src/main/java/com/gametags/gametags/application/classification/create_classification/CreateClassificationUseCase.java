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
    //PUEDE QUE YA ESTE SOLUCIONADO
    //ESTO NO TIENE MUCHO SENTIDO YA QUE PODRIA HABER VARIAS CLASIFICACIONES IGUALES PERO CON DISTINTO ID
    //SE DEBERIA COMPROBAR CONSULTANDO SI YA EXISTE UNA CLASIFICACION DE LA MISMA ENTIDAD EN EL VIDEOJUEGO

    log.info("[START] Creating classification with data: " + classification.getId() + classification.getSystem() + classification.getCountry());
    Classification previous = service.findOneClassification(classification.getId());
    if (Objects.isNull(previous.getId()) && ObjectUtils.isEmpty(service.findOneClassificationBySystemAndTag(classification.getSystem(), classification.getTag()))) {
      log.info("[STOP] createClassification");
      return service.createClassification(classification);
    }else{
      throw new AlreadyBuiltException("Classification already exists");
    }
  }
}
