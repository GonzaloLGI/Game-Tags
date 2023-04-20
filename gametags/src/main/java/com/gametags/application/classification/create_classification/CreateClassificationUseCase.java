package com.gametags.application.classification.create_classification;

import com.gametags.domain.Classification;
import com.gametags.domain.ClassificationService;
import com.gametags.infrastructure.ClassificationDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

@Slf4j
public class CreateClassificationUseCase {

    @Autowired
    private ClassificationService service;

    public Classification createClassification(Classification classification) {
        //ESTO NO TIENE MUCHO SENTIDO YA QUE PODRIA HABER VARIAS CLASIFICACIONES IGUALES PERO CON DISTINTO ID
        //SE DEBERIA COMPROBAR CONSULTANDO SI YA EXISTE UNA CLASIFICACION DE LA MISMA ENTIDAD EN EL VIDEOJUEGO
        Classification previous = service.findOneClassification(classification.getId());
        if(ObjectUtils.isEmpty(previous)){
            return service.createClassification(classification);
        }
        return previous;
    }
}
