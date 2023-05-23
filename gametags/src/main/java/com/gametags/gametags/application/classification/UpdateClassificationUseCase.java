package com.gametags.gametags.application.classification;

import com.gametags.gametags.domain.model.Classification;
import com.gametags.gametags.domain.services.ClassificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Objects;

@Component
@Slf4j
public class UpdateClassificationUseCase {
    @Autowired
    private ClassificationService service;
    public Classification updateClassification(Classification classification) {
        log.debug("[START] updateClassification");
        Classification foundClassification = service.findOneClassification(classification.getId());
        if(Objects.isNull(foundClassification)){
            throw new NoSuchElementException("The classification is not registered");
        }
        log.debug("[STOP] updateClassification");
        return service.updateClassification(classification);
    }
}
