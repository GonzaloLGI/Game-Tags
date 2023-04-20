package com.gametags.application.classification.create_classification;

import com.gametags.domain.ClassificationService;
import com.gametags.infrastructure.ClassificationDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

@Slf4j
public class CreateClassificationUseCase {

    @Autowired
    private ClassificationService service;

    public ClassificationDAO createClassification(ClassificationDAO dao) {
        //ESTO NO TIENE MUCHO SENTIDO YA QUE PODRIA HABER VARIAS CLASIFICACIONES IGUALES PERO CON DISTINTO ID
        //SE DEBERIA COMPROBAR CONSULTANDO SI YA EXISTE UNA CLASIFICACION DE LA MISMA ENTIDAD EN EL VIDEOJUEGO
        ClassificationDAO previous = service.findOneClassification(dao.getId());
        if(ObjectUtils.isEmpty(previous)){
            return service.createClassification(dao);
        }
        return previous;
    }
}
