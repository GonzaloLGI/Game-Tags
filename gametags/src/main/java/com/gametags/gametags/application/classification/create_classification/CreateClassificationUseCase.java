package com.gametags.gametags.application.classification.create_classification;

import com.gametags.gametags.domain.Classification;
import com.gametags.gametags.domain.ClassificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

@Component
@Slf4j
public class CreateClassificationUseCase {

    @Autowired
    private ClassificationService service;

    public Classification createClassification(Classification classification) {
        //ESTO NO TIENE MUCHO SENTIDO YA QUE PODRIA HABER VARIAS CLASIFICACIONES IGUALES PERO CON DISTINTO ID
        //SE DEBERIA COMPROBAR CONSULTANDO SI YA EXISTE UNA CLASIFICACION DE LA MISMA ENTIDAD EN EL VIDEOJUEGO
        log.info("Creating classification with data: " + classification.getId() + classification.getSystem() + classification.getCountry());
        Classification previous = service.findOneClassification(classification.getId());
        if(Objects.isNull(previous.getId())){
            return service.createClassification(classification);
        }
        return previous;
    }
}
