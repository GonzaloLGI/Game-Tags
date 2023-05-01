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
public class FindClassificationByIdUseCase {
    @Autowired
    private ClassificationService service;
    public Classification findOneClassification(UUID id) {
        log.info("Searching classification with id: " + id);
        Classification classification = service.findOneClassification(id);
        if(Objects.isNull(classification)){
            throw new NoSuchElementException("The classification is not registered");
        }
        return classification;
    }
}
