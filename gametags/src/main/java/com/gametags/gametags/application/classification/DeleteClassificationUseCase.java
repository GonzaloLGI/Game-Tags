package com.gametags.gametags.application.classification;

import com.gametags.gametags.domain.model.Classification;
import com.gametags.gametags.domain.services.ClassificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Component
@Slf4j
public class DeleteClassificationUseCase {
    @Autowired
    private ClassificationService service;
    public Classification deleteClassification(UUID id) {
        log.info("Deleting classification with id: " + id);
        Classification classification = service.findOneClassification(id);
        if(!Objects.isNull(classification.getId())){
            return service.deleteClassification(id);
        }else{
            throw new NoSuchElementException("Non existing classification");
        }

    }
}
